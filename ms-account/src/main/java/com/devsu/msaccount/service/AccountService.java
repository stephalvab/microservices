package com.devsu.msaccount.service;

import com.devsu.msaccount.model.dto.AccountDto;
import com.devsu.msaccount.model.dto.AccountSaveDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<AccountDto> getAllAccounts();

    Mono<AccountDto> getAccountById(int id);

    Mono<AccountDto> saveAccount(AccountSaveDto AccountSaveDto);

    Mono<AccountDto> updateAccount(int id, AccountSaveDto AccountUpdateDto);

    Mono<Void> deleteAccountById(int id);
}
