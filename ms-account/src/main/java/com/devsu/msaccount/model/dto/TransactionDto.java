package com.devsu.msaccount.model.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class TransactionDto {
    private int transactionId;
    private int accountId;
    private Date dateRegister;
    private double amount;
    private double availableBalance;
    private boolean status;
}
