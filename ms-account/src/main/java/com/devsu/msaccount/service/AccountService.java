package com.devsu.msaccount.service;

import com.devsu.msaccount.model.dto.AccountDto;
import com.devsu.msaccount.model.dto.AccountSaveDto;

import java.util.List;

public interface AccountService {
    List<AccountDto> getAllAccounts();

    AccountDto getAccountById(int id);

    AccountDto saveAccount(AccountSaveDto AccountSaveDto);

    AccountDto updateAccount(int id, AccountSaveDto AccountUpdateDto);

    void deleteAccountById(int id);
}
