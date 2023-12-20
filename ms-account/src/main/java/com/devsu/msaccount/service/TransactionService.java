package com.devsu.msaccount.service;

import com.devsu.msaccount.model.dto.TransactionDto;
import com.devsu.msaccount.model.dto.TransactionSaveDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
    Flux<TransactionDto> getAllTransactions();

    Mono<TransactionDto> getTransactionById(int id);

    Mono<TransactionDto> saveTransaction(TransactionSaveDto TransactionSaveDto);

    Mono<TransactionDto> updateTransaction(int id, TransactionSaveDto TransactionUpdateDto);

    Mono<Void> deleteTransactionById(int id);
}



