package com.devsu.msaccount.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountSaveDto {
    private String accountNumber;
    private int idAccountType;
    private int idClient;
    private double balance;
    private boolean status;
}
