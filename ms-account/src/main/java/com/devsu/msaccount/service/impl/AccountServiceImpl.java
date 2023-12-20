package com.devsu.msaccount.service.impl;

import com.devsu.msaccount.exception.NotFoundException;
import com.devsu.msaccount.model.dto.AccountDto;
import com.devsu.msaccount.model.dto.AccountSaveDto;
import com.devsu.msaccount.model.entity.Account;
import com.devsu.msaccount.model.entity.AccountType;
import com.devsu.msaccount.model.entity.Client;
import com.devsu.msaccount.repository.AccountRepository;
import com.devsu.msaccount.service.AccountService;
import com.devsu.msaccount.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Flux<AccountDto> getAllAccounts() {
        return Flux.fromStream(accountRepository.findAll().stream()
                .map(this::builderAccountDto));
    }

    @Override
    public Mono<AccountDto> getAccountById(int id) {
        return Mono.justOrEmpty(accountRepository.findById(id)
                        .map(this::builderAccountDto))
                .switchIfEmpty(Mono.error(new NotFoundException(Constants.ACCOUNT_NOT_FOUND)));
    }

    @Override
    public Mono<AccountDto> saveAccount(AccountSaveDto accountDto) {
        Account account = new Account();
        account.setStatus(true);
        return Mono.just(builderAccountDto(accountRepository
                .save(builderAccountBd(accountDto, account))));
    }

    @Override
    public Mono<AccountDto> updateAccount(int id, AccountSaveDto accountSaveDto) {
        Account accountBd = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.ACCOUNT_NOT_FOUND));
        accountBd.setStatus(accountSaveDto.isStatus());
        return Mono.just(builderAccountDto(accountRepository
                .save(builderAccountBd(accountSaveDto, accountBd))));
    }

    @Override
    public Mono<Void> deleteAccountById(int id) {
        try {
            accountRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(Constants.ACCOUNT_NOT_FOUND);
        }
        return Mono.empty();}

    private AccountDto builderAccountDto(Account account) {
        return AccountDto.builder()
                .accountId(account.getAccountId())
                .accountNumber(account.getAccountNumber())
                .accountTypeId(account.getAccountType().getAccountTypeId())
                .clientId(account.getClient().getClientId())
                .balance(account.getBalance())
                .status(account.isStatus())
                .build();
    }

    private Account builderAccountBd(AccountSaveDto accountSaveDto, Account account) {
        Client client = new Client();
        client.setClientId(accountSaveDto.getIdClient());
        account.setAccountNumber(accountSaveDto.getAccountNumber());
        account.setAccountType(AccountType.builder()
                .accountTypeId(accountSaveDto.getIdAccountType())
                .build());
        account.setBalance(accountSaveDto.getBalance());
        account.setClient(client);
        return account;
    }
}