package com.devsu.hackerearth.backend.account.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.controller.ExceptionHandler.InsufficientFundsException;
import com.devsu.hackerearth.backend.account.controller.ExceptionHandler.NotFoundException;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.ClientDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.model.mapper.BankStatementMapper;
import com.devsu.hackerearth.backend.account.model.mapper.TransactionMapper;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final ClientService clientService;

	public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService, ClientService clientService) {
		this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.clientService = clientService;
	}

    @Override
    public List<TransactionDto> getAll() {
        // Get all transactions
		return TransactionMapper.toDto(transactionRepository.findAll());
    }

    @Override
    public TransactionDto getById(Long id) {
        // Get transactions by id
		return TransactionMapper.toDto(findById(id));
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        // Create transaction
        balanceOperation(transactionDto);
        Transaction transaction = TransactionMapper.toEntity(transactionDto);
        transaction.setId(null);
        transactionRepository.save(transaction);
        return TransactionMapper.toDto(transaction);
    }
        
    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {
        // Report
        List<AccountDto> accounts = accountService.getAllByClientId(clientId);
        if (accounts.isEmpty()) {
            return Collections.emptyList();
        }

        List<Transaction> transactions = accounts.stream()
            .map(account -> transactionRepository.findAllByAccountIdAndDateBetween(clientId, dateTransactionStart, dateTransactionEnd))
            .flatMap(List::stream) // Aplanar la lista de listas de transacciones
            .collect(Collectors.toList());

        if (transactions.isEmpty()) {
            return Collections.emptyList();
        }

            // Crear un mapa de AccountDto por ID
        Map<Long, AccountDto> accountMap = accounts.stream()
            .collect(Collectors.toMap(AccountDto::getId, account -> account));

        // Obtener el nombre del cliente
        ClientDto client = clientService.getClientById(clientId);
        String clientName = "";
        if (Objects.nonNull(client)) clientName = client.getName();

        // Convertir transacciones a BankStatementDto usando el mapper optimizado
        return BankStatementMapper.toDtoList(transactions, accountMap, clientName);
    } 
    
    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        // If you need it
        Transaction transaction = transactionRepository.findTopByAccountIdOrderByDateDesc(accountId)
            .orElseThrow(() -> new NotFoundException("Not found transaction for accountId "+ accountId));

		return TransactionMapper.toDto(transaction);
    }
    
	private Transaction findById(Long id){
		return transactionRepository.findById(id).orElseThrow(() -> new NotFoundException("Transacction not found for id " + id));
	}

    private void balanceOperation(TransactionDto transactionDto) {
        double tempBalance = getBalance(transactionDto.getAccountId())+transactionDto.getAmount();
        if (tempBalance < 0){
            throw new InsufficientFundsException("Saldo no disponible");
        }
        transactionDto.setBalance(tempBalance);
    }

    private double getBalance(Long accountId) {
        Optional<Transaction> lastTransaction = transactionRepository.findTopByAccountIdOrderByDateDesc(accountId);
        if (lastTransaction.isPresent()) {
            return lastTransaction.get().getBalance();
        }
        return accountService.getById(accountId).getInitialAmount();
    }
}
