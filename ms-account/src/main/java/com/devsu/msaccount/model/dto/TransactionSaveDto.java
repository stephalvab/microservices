package com.devsu.msaccount.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class TransactionSaveDto {

    private int accountId;
    private double amount;
    private boolean status;
}
