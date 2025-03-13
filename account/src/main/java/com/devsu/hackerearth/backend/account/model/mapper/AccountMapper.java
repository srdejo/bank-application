package com.devsu.hackerearth.backend.account.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;

public class AccountMapper {

    public static AccountDto toDto(Account account) {
        if (account == null) {
            return null;
        }

        return new AccountDto(
            account.getId(),
            account.getNumber(),
            account.getType(),
            account.getInitialAmount(),
            account.isActive(),
            account.getClientId()
        );
    }

    public static Account toEntity(AccountDto accountDto) {
        if (accountDto == null) {
            return null;
        }

        Account account = new Account();
        return getAccount(accountDto, account);
    }


    public static Account toEntity(Account account, AccountDto accountDto) {
        if (accountDto == null) {
            return null;
        }
        return getAccount(accountDto, account);
    }

    public static List<AccountDto> toDto(List<Account> accounts) {
        if (accounts == null) {
            return null;
        }

        return accounts.stream()
                .map(AccountMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Account> toEntity(List<AccountDto> accountDtos) {
        if (accountDtos == null) {
            return null;
        }

        return accountDtos.stream()
                .map(AccountMapper::toEntity)
                .collect(Collectors.toList());
    }


    private static Account getAccount(AccountDto accountDto, Account account) {
        account.setId(accountDto.getId());
        account.setNumber(accountDto.getNumber());
        account.setType(accountDto.getType());
        account.setInitialAmount(accountDto.getInitialAmount());
        account.setActive(accountDto.isActive());
        account.setClientId(accountDto.getClientId());

        return account;
    }
}
