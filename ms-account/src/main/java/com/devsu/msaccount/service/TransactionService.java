package com.devsu.msaccount.service;

import com.devsu.msaccount.model.dto.AccountDto;
import com.devsu.msaccount.model.dto.AccountSaveDto;
import com.devsu.msaccount.model.dto.TransactionDto;
import com.devsu.msaccount.model.dto.TransactionSaveDto;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> getAllTransactions();

    TransactionDto getTransactionById(int id);

    TransactionDto saveTransaction(TransactionSaveDto TransactionSaveDto);

    TransactionDto updateTransaction(int id, TransactionSaveDto TransactionUpdateDto);

    void deleteTransactionById(int id);
}



