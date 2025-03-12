package com.devsu.hackerearth.backend.account.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;

public class TransactionMapper {
    
    // Convertir de Transaction a TransactionDto
    public static TransactionDto toDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        return new TransactionDto(
            transaction.getId(),
            transaction.getDate(),
            transaction.getType(),
            transaction.getAmount(),
            transaction.getBalance(),
            transaction.getAccountId()
        );
    }

    // Convertir de TransactionDto a Transaction
    public static Transaction toEntity(TransactionDto transactionDto) {
        if (transactionDto == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setDate(transactionDto.getDate());
        transaction.setType(transactionDto.getType());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setBalance(transactionDto.getBalance());
        transaction.setAccountId(transactionDto.getAccountId());
        return transaction;
    }

    // Convertir lista de Transaction a lista de TransactionDto
    public static List<TransactionDto> toDto(List<Transaction> transactions) {
        return transactions.stream()
            .map(TransactionMapper::toDto)
            .collect(Collectors.toList());
    }

    // Convertir lista de TransactionDto a lista de Transaction
    public static List<Transaction> toEntity(List<TransactionDto> transactionDtos) {
        return transactionDtos.stream()
            .map(TransactionMapper::toEntity)
            .collect(Collectors.toList());
    }
}
