package com.devsu.hackerearth.backend.account.controller;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@GetMapping()
    public ResponseEntity<List<TransactionDto>> getAll(){
		// api/transactions
		// Get all transactions
		return ResponseEntity.ok(transactionService.getAll());
	}

	@GetMapping("/{id}")
    public ResponseEntity<TransactionDto> get(@PathVariable Long id){
		// api/transactions/{id}
		// Get transactions by id
		return ResponseEntity.ok(transactionService.getById(id));
	}

	@GetMapping("/clients/{clientId}/report")
    public ResponseEntity<List<BankStatementDto>> report(@PathVariable Long clientId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionStart,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionEnd) {
        // api/transactions/clients/{clientId}/report
        // Get report
        return ResponseEntity.ok(transactionService.getAllByAccountClientIdAndDateBetween(clientId, dateTransactionStart, dateTransactionEnd));
    }

	@PostMapping()
	public ResponseEntity<TransactionDto> create(@RequestBody TransactionDto transactionDto){
		// api/transactions
		// Create transactions
		return new ResponseEntity<>(transactionService.create(transactionDto), HttpStatus.CREATED);
	}

}
