package com.devsu.hackerearth.backend.account.model.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;

public class BankStatementMapper {
 
    public static BankStatementDto toDto(Transaction transaction, AccountDto account, String clientName) {
        if (transaction == null || account == null) {
            throw new IllegalArgumentException("Transaction or Account cannot be null");
        }

        return new BankStatementDto(
            transaction.getDate(),
            clientName,
            account.getNumber(),
            account.getType(),
            account.getInitialAmount(),
            account.isActive(),
            transaction.getType(),
            transaction.getAmount(),
            transaction.getBalance()
        );
    }

    public static List<BankStatementDto> toDtoList(List<Transaction> transactions, Map<Long, AccountDto> accountMap, String clientName) {
        return transactions.stream()
            .map(transaction -> {
                AccountDto account = accountMap.get(transaction.getAccountId());
                return toDto(transaction, account, clientName);
            })
            .collect(Collectors.toList());
    }
}
