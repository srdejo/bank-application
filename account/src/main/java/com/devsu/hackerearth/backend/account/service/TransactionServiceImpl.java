package com.devsu.hackerearth.backend.account.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.controller.ExceptionHandler.InsufficientFundsException;
import com.devsu.hackerearth.backend.account.controller.ExceptionHandler.NotFoundException;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.model.mapper.TransactionMapper;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;

	public TransactionServiceImpl(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
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
    public List<TransactionDto> getAllByAccountClientIdAndDateBetween(Long accountId, Date dateTransactionStart,
            Date dateTransactionEnd) {
        // Report
		return TransactionMapper.toDto(transactionRepository.findAllByAccountIdAndDateBetween(accountId, dateTransactionStart, dateTransactionEnd));
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
        double tempBalance = transactionDto.getBalance()+transactionDto.getAmount();
        if (tempBalance < 0){
            throw new InsufficientFundsException("Saldo no disponible");
        }
        transactionDto.setBalance(tempBalance);
    }
}
