package com.devsu.hackerearth.backend.account.service;

import java.util.Date;
import java.util.List;

import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;

public interface BankStatementService{
    
    public List<BankStatementDto> getBankStatement(Long accountId, Date startDate, Date endDate);
}
