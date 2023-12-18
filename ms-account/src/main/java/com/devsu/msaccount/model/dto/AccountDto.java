package com.devsu.msaccount.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {
    private int accountId;
    private String accountNumber;
    private int idAccountType;
    private int idClient;
    private double amount;
    private boolean status;
}
