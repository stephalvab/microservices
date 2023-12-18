package com.devsu.msaccount.service.impl;

import com.devsu.msaccount.exception.NotFoundException;
import com.devsu.msaccount.model.dto.AccountDto;
import com.devsu.msaccount.model.dto.AccountSaveDto;
import com.devsu.msaccount.model.entity.Account;
import com.devsu.msaccount.repository.AccountRepository;
import com.devsu.msaccount.service.AccountService;
import com.devsu.msaccount.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::builderAccountDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getAccountById(int id) {
        return accountRepository.findById(id)
                .map(this::builderAccountDto)
                .orElseThrow(() -> new NotFoundException(Constants.ACCOUNT_NOT_FOUND));
    }

    @Override
    public AccountDto saveAccount(AccountSaveDto accountDto) {
        return builderAccountDto(accountRepository
                .save(builderAccountBd(accountDto, new Account())));
    }

    @Override
    public AccountDto updateAccount(int id, AccountSaveDto accountSaveDto) {
        Account accountBd = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.ACCOUNT_NOT_FOUND));
        Account account = accountRepository.save(builderAccountBd(accountSaveDto, accountBd));
        return builderAccountDto(account);
    }

    @Override
    public void deleteAccountById(int id) {
        try {
            accountRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(Constants.ACCOUNT_NOT_FOUND);
        }
    }

    private AccountDto builderAccountDto(Account account) {
        return AccountDto.builder()
                .accountId(account.getAccountId())
                .accountNumber(account.getAccountNumber())

                .build();
    }

    private Account builderAccountBd(AccountSaveDto accountSaveDto, Account account) {


        return account;
    }
}