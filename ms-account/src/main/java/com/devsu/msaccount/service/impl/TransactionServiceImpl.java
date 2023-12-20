package com.devsu.msaccount.service.impl;


import com.devsu.msaccount.exception.NotFoundException;
import com.devsu.msaccount.model.dto.AccountDto;
import com.devsu.msaccount.model.dto.AccountSaveDto;
import com.devsu.msaccount.model.dto.TransactionDto;
import com.devsu.msaccount.model.dto.TransactionSaveDto;
import com.devsu.msaccount.model.entity.Account;
import com.devsu.msaccount.model.entity.Transaction;
import com.devsu.msaccount.repository.TransactionRepository;
import com.devsu.msaccount.service.AccountService;
import com.devsu.msaccount.service.TransactionService;
import com.devsu.msaccount.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;


@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public Flux<TransactionDto> getAllTransactions() {
        return Flux.fromStream(transactionRepository.findAll().stream()
                .map(this::builderTransactionDto));
    }

    @Override
    public Mono<TransactionDto> getTransactionById(int id) {
        return Mono.justOrEmpty(transactionRepository.findById(id)
                        .map(this::builderTransactionDto))
                .switchIfEmpty(Mono.error(new NotFoundException(Constants.TRANSACTION_NOT_FOUND)));
    }

    @Override
    public Mono<TransactionDto> saveTransaction(TransactionSaveDto transactionDto) {
        Mono<AccountDto> accountMono = accountService.getAccountById(transactionDto.getAccountId());

        return accountMono.flatMap(accountDto -> {
            if (transactionDto.getAmount() <= 0 &&
                    accountDto.getBalance() <= Math.abs(transactionDto.getAmount())) {
                return Mono.error(new Exception(Constants.INSUFFICIENT_BALANCE));
            }
            accountDto.setBalance(accountDto.getBalance() + transactionDto.getAmount());
            return accountService.updateAccount(accountDto.getAccountId(),
                            buildAccountSaveDto(accountDto))
                    .then(Mono.fromSupplier(() -> {
                        Transaction transaction = new Transaction();
                        transaction.setStatus(true);
                        transaction.setDateRegister(new Date());
                        transaction.setAvailableBalance(accountDto.getBalance());
                        return builderTransactionDto(transactionRepository
                                .save(builderTransactionBd(transactionDto, transaction)));
                    }));
        });
    }

    @Override
    public Mono<TransactionDto> updateTransaction(int id, TransactionSaveDto transactionSaveDto) {
        Transaction transactionBd = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.ACCOUNT_NOT_FOUND));
        transactionBd.setStatus(transactionSaveDto.isStatus());
        return Mono.just(builderTransactionDto(transactionRepository
                .save(builderTransactionBd(transactionSaveDto, transactionBd))));
    }

    @Override
    public Mono<Void> deleteTransactionById(int id) {
        try {
            transactionRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(Constants.TRANSACTION_NOT_FOUND);
        }
        return Mono.empty();
    }

    private TransactionDto builderTransactionDto(Transaction transaction) {
        return TransactionDto.builder()
                .transactionId(transaction.getTransactionId())
                .accountId(transaction.getAccount().getAccountId())
                .amount(transaction.getAmount())
                .availableBalance(transaction.getAvailableBalance())
                .dateRegister(transaction.getDateRegister())
                .status(transaction.isStatus())
                .build();
    }

    private Transaction builderTransactionBd(TransactionSaveDto transactionSaveDto, Transaction transaction) {
        Account account = new Account();
        account.setAccountId(transactionSaveDto.getAccountId());
        transaction.setAmount(transactionSaveDto.getAmount());
        transaction.setAccount(account);
        return transaction;
    }

    private AccountSaveDto buildAccountSaveDto(AccountDto accountDto) {
        return AccountSaveDto.builder()
                .idClient(accountDto.getClientId())
                .idAccountType(accountDto.getAccountTypeId())
                .accountNumber(accountDto.getAccountNumber())
                .balance(accountDto.getBalance())
                .status(accountDto.isStatus())
                .build();
    }

    private Mono<Void> validateBalance(TransactionSaveDto transactionDto, double amountBalance) {
        if (transactionDto.getAmount() <= 0 &&
                amountBalance <= Math.abs(transactionDto.getAmount())) {
            return Mono.error(new Exception(Constants.INSUFFICIENT_BALANCE));
        }
        return Mono.empty();
    }
}