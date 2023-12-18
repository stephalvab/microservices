package com.devsu.msaccount.service.impl;


import com.devsu.msaccount.exception.NotFoundException;
import com.devsu.msaccount.model.dto.TransactionDto;
import com.devsu.msaccount.model.dto.TransactionSaveDto;
import com.devsu.msaccount.model.entity.Transaction;
import com.devsu.msaccount.repository.TransactionRepository;
import com.devsu.msaccount.service.TransactionService;
import com.devsu.msaccount.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(this::builderTransactionDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto getTransactionById(int id) {
        return transactionRepository.findById(id)
                .map(this::builderTransactionDto)
                .orElseThrow(() -> new NotFoundException(Constants.ACCOUNT_NOT_FOUND));
    }

    @Override
    public TransactionDto saveTransaction(TransactionSaveDto transactionDto) {
        return builderTransactionDto(transactionRepository
                .save(builderTransactionBd(transactionDto, new Transaction())));
    }

    @Override
    public TransactionDto updateTransaction(int id, TransactionSaveDto transactionSaveDto) {
        Transaction transactionBd = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.ACCOUNT_NOT_FOUND));
        Transaction transaction = transactionRepository.save(builderTransactionBd(transactionSaveDto, transactionBd));
        return builderTransactionDto(transaction);
    }

    @Override
    public void deleteTransactionById(int id) {
        try {
            transactionRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(Constants.TRANSACTION_NOT_FOUND);
        }
    }

    private TransactionDto builderTransactionDto(Transaction transaction) {
        return TransactionDto.builder()


                .build();
    }

    private Transaction builderTransactionBd(TransactionSaveDto transactionSaveDto, Transaction transaction) {


        return transaction;
    }
}

