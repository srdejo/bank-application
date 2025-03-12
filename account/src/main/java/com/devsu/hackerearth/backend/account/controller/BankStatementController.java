package com.devsu.hackerearth.backend.account.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.service.BankStatementService;

@RestController
@RequestMapping("/api/transactions/clients")
public class BankStatementController {

    private final BankStatementService bankStatementService;

    public BankStatementController(BankStatementService bankStatementService) {
        this.bankStatementService = bankStatementService;
    }

    @GetMapping("/{clientId}/report")
    public ResponseEntity<List<BankStatementDto>> report(@PathVariable Long clientId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionStart,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionEnd) {
        // api/transactions/clients/{clientId}/report
        // Get report
        return ResponseEntity.ok(bankStatementService.getBankStatement(clientId, dateTransactionStart, dateTransactionEnd));
    }
}
