package com.devsu.msaccount.service;

import com.devsu.msaccount.model.dto.AccountStatementDto;
import reactor.core.publisher.Flux;

import java.util.Date;

public interface MsClientService {
    Flux<AccountStatementDto> generateAccountStatement(Date startDate, Date endDate, int clientId);
}
