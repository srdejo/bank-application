package com.devsu.hackerearth.backend.account.service;


import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.ClientDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.model.mapper.BankStatementMapper;

@Service
public class BankStatementServiceImpl implements BankStatementService {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final ClientService clientService;

    public BankStatementServiceImpl(TransactionService transactionService,
    AccountService accountService,
    ClientService clientService
    ) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.clientService = clientService;
    }

    @Override
    public List<BankStatementDto> getBankStatement(Long clientId, Date startDate, Date endDate) {
        // Obtener cuentas del cliente
        List<AccountDto> accounts = accountService.getAllByClientId(clientId);
        if (accounts.isEmpty()) {
            return Collections.emptyList();
        }

        List<TransactionDto> transactions = accounts.stream()
            .map(account -> transactionService.getAllByAccountClientIdAndDateBetween(account.getId(), startDate, endDate))
            .flatMap(List::stream) // Aplanar la lista de listas de transacciones
            .collect(Collectors.toList());

        if (transactions.isEmpty()) {
            return Collections.emptyList();
        }

            // Crear un mapa de AccountDto por ID
        Map<Long, AccountDto> accountMap = accounts.stream()
            .collect(Collectors.toMap(AccountDto::getId, account -> account));

        // Obtener el nombre del cliente
        ResponseEntity<ClientDto> responseEntityClient = clientService.getClientById(clientId);
        String clientName = "";
        if (responseEntityClient.getStatusCode().equals(HttpStatus.OK) && Objects.nonNull(responseEntityClient.getBody())) clientName = Objects.requireNonNull(responseEntityClient.getBody()).getName();

        // Convertir transacciones a BankStatementDto usando el mapper optimizado
        return BankStatementMapper.toDtoList(transactions, accountMap, clientName);
    } 
}
