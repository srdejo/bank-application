package com.devsu.hackerearth.backend.client.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.client.controller.ExceptionHandler.EntityOverwriteException;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	private final ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@GetMapping()
	public ResponseEntity<List<ClientDto>> getAll(){
		// api/clients
		// Get all clients
		return ResponseEntity.ok(clientService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientDto> get(@PathVariable Long id){
		// api/clients/{id}
		// Get clients by id
		return ResponseEntity.ok(clientService.getById(id));
	}

	@PostMapping()
	public ResponseEntity<ClientDto> create(@RequestBody ClientDto clientDto){
		// api/clients
		// Create client
		return new ResponseEntity<>(clientService.create(clientDto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClientDto> update(@PathVariable Long id, @RequestBody ClientDto clientDto){
		// api/clients/{id}
		// Update client
		clientService.getById(id);
		if (Objects.isNull(clientDto.getId())) clientDto.setId(id);
		if (!id.equals(clientDto.getId())) throw new EntityOverwriteException("Cannot overwrite existing account with a different Id");
		return ResponseEntity.ok(clientService.update(clientDto));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ClientDto> partialUpdate(@PathVariable Long id, @RequestBody PartialClientDto partialClientDto){
		// api/accounts/{id}
		// Partial update accounts
		return ResponseEntity.ok(clientService.partialUpdate(id, partialClientDto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		// api/clients/{id}
		// Delete client
		clientService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
