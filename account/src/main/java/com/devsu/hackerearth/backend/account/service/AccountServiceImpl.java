package com.devsu.hackerearth.backend.account.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.controller.ExceptionHandler.NotFoundException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.model.mapper.AccountMapper;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;

	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

    @Override
    public List<AccountDto> getAll() {
        // Get all accounts
		return AccountMapper.toDto(accountRepository.findAll());
    }

    
    @Override
    public List<AccountDto> getAllByClientId(Long clientId) {
		return AccountMapper.toDto(accountRepository.getAllByClientId(clientId));
    }

    @Override
    public AccountDto getById(Long id) {
        // Get accounts by id
		return AccountMapper.toDto(findById(id));
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        // Create account
		return null;
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        // Update account
        Account account = AccountMapper.toEntity(accountDto);
        account.setId(null);
        accountRepository.save(account);
		return AccountMapper.toDto(account);
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        // Partial update account
        Account account = findById(id);
        account.setActive(partialAccountDto.isActive());
        accountRepository.save(account);
		return AccountMapper.toDto(account);
    }

    @Override
    public void deleteById(Long id) {
        // Delete account
        Account account = findById(id);
        accountRepository.delete(account);
    }
    
	private Account findById(Long id){
		return accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found for id " + id));
	}

}
