package com.devsu.msaccount.controller;

import com.devsu.msaccount.model.dto.AccountStatementDto;
import com.devsu.msaccount.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Date;

@RestController
@RequestMapping("/reportes")
public class ReportController {

    @Autowired
    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<AccountStatementDto> generateAccountStatement(
            @RequestParam("fechaInicio") @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaFin,
            @RequestParam("cliente") int clienteId) {

        return reportService.generateAccountStatement(fechaInicio, fechaFin, clienteId);
    }
}

