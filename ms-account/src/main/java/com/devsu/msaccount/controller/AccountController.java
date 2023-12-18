package com.devsu.msaccount.controller;

import com.devsu.msaccount.exception.MessageException;
import com.devsu.msaccount.exception.NotFoundException;
import com.devsu.msaccount.model.dto.AccountDto;
import com.devsu.msaccount.model.dto.AccountSaveDto;
import com.devsu.msaccount.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class AccountController {

    @Autowired
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<?> getAllAccounts() {
        try {
            List<AccountDto> accounts = accountService.getAllAccounts();
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageException.Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable int id) {
        try {
            AccountDto account = accountService.getAccountById(id);
            return ResponseEntity.ok(account);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageException.Message(HttpStatus.NOT_FOUND, e.getLocalizedMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageException.Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage()));
        }
    }

    @Validated
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody @Valid AccountSaveDto accountSaveDto) {
        try {
            AccountDto createdAccount = accountService.saveAccount(accountSaveDto);
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageException.Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage()));
        }
    }

    @Validated
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable int id, @RequestBody @Valid AccountSaveDto accountSaveDto) {
        try {
            AccountDto updatedAccount = accountService.updateAccount(id, accountSaveDto);
            return new ResponseEntity<>(updatedAccount, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageException.Message(HttpStatus.NOT_FOUND, e.getLocalizedMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageException.Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccountById(@PathVariable int id) {
        try {
            accountService.deleteAccountById(id);
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
