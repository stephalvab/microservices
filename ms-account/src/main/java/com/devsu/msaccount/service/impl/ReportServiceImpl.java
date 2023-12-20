package com.devsu.msaccount.service.impl;

import com.devsu.msaccount.model.dto.AccountStatementDto;
import com.devsu.msaccount.model.dto.ClientDto;
import com.devsu.msaccount.model.entity.Account;
import com.devsu.msaccount.model.entity.Transaction;
import com.devsu.msaccount.repository.TransactionRepository;
import com.devsu.msaccount.service.ReportService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class ReportServiceImpl implements ReportService {
    private final WebClient webClient;

    @Autowired
    private TransactionRepository transactionRepository;

    public ReportServiceImpl(WebClient.Builder webClientBuilder, TransactionRepository transactionRepository) {
        this.webClient = webClientBuilder.baseUrl("${ms-client.url}").build();
        this.transactionRepository = transactionRepository;
    }

    public Mono<ClientDto> getClientById(int clientId) {
        return webClient.get()
                .uri("${ms-client.getclient}", clientId)
                .retrieve()
                .bodyToMono(ClientDto.class);
    }

    @Override
    public Flux<AccountStatementDto> generateAccountStatement(Date startDate, Date endDate, int clientId) {
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        return Flux.fromStream(transactionRepository
                .findTransactionsByClientAndDateRange(clientId, startDate, endDate)
                .stream()
                .map(transaction ->  AccountStatementDto.builder()
                        .fecha(formato.format(transaction.getDateRegister()))
                        .cliente(transaction.getAccount().getClient().getName())
                        .numeroCuenta(transaction.getAccount().getAccountNumber())
                        .tipo(transaction.getAccount().getAccountType().getName())
                        .saldoInicial(transaction.getAmount() - transaction.getAvailableBalance())
                        .estado(transaction.isStatus())
                        .movimiento(transaction.getAmount())
                        .saldoDisponible(transaction.getAvailableBalance())
                        .build())
        );
    }

}