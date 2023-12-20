package com.devsu.msaccount.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {
    private int accountId;
    private String accountNumber;
    private int accountTypeId;
    private int clientId;
    private double balance;
    private boolean status;
}
