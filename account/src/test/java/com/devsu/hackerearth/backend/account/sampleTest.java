package com.devsu.hackerearth.backend.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.account.controller.AccountController;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.service.AccountService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class sampleTest {

	private AccountService accountService = mock(AccountService.class);
	private AccountController accountController = new AccountController(accountService);

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void createAccountTest() {
		// Arrange
		AccountDto newAccount = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		AccountDto createdAccount = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		when(accountService.create(newAccount)).thenReturn(createdAccount);

		// Act
		ResponseEntity<AccountDto> response = accountController.create(newAccount);

		// Assert
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(createdAccount, response.getBody());
	}

	@Test
	void updateAccountTest() {
		// Arrange
		AccountDto newAccount = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		AccountDto createdAccount = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		when(accountService.update(newAccount)).thenReturn(createdAccount);

		// Act
		ResponseEntity<AccountDto> response = accountController.update(1L, newAccount);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(createdAccount, response.getBody());
	}

	@Test
	void getAccountNotFoundTest() {
		// Act: Llamar al endpoint con un ID inexistente
		try {
			this.restTemplate.getForObject("http://localhost:" + port + "/api/accounts/999",
					String.class);
		} catch (Exception e) {
			assertEquals("Account not found for id 999", e.getMessage());
		}
	}

}
