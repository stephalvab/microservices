package com.devsu.msaccount.controller;

import com.devsu.msaccount.exception.MessageException;
import com.devsu.msaccount.exception.NotFoundException;
import com.devsu.msaccount.model.dto.AccountSaveDto;
import com.devsu.msaccount.model.dto.TransactionSaveDto;
import com.devsu.msaccount.service.AccountService;
import com.devsu.msaccount.service.TransactionService;
import com.devsu.msaccount.util.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/movimiento")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTransactions() {
        try {
            return new ResponseEntity<>(transactionService.getAllTransactions(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageException.Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(transactionService.getTransactionById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageException.Message(HttpStatus.NOT_FOUND, e.getLocalizedMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageException.Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage()));
        }
    }

    @PostMapping
    @Validated
    public ResponseEntity<?> createTransaction(@RequestBody @Valid TransactionSaveDto transactionSaveDto) {
        try {
            return new ResponseEntity<>(transactionService.saveTransaction(transactionSaveDto), HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageException.Message(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_BD));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageException.Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage()));
        }
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<?> updateTransaction(@PathVariable int id, @Valid @RequestBody TransactionSaveDto transactionSaveDto) {
        try {
            return new ResponseEntity<>(transactionService.updateTransaction(id, transactionSaveDto), HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageException.Message(HttpStatus.INTERNAL_SERVER_ERROR,Constants.ERROR_BD));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageException.Message(HttpStatus.NOT_FOUND, e.getLocalizedMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageException.Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransactionById(@PathVariable int id) {
        try {
            transactionService.deleteTransactionById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageException.Message(HttpStatus.NOT_FOUND, e.getLocalizedMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageException.Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage()));
        }
    }
}
