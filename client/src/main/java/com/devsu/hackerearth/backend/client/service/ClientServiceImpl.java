package com.devsu.hackerearth.backend.client.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.controller.ExceptionHandler.NotFoundException;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.model.mapper.ClientMapper;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;

	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public List<ClientDto> getAll() {
		// Get all clients
		return ClientMapper.toDto(clientRepository.findAll());
	}

	@Override
	public ClientDto getById(Long id) {
		// Get clients by id

		return ClientMapper.toDto(findById(id));
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		// Create client
		Client client = ClientMapper.toEntity(clientDto);
		clientRepository.save(client);
		return ClientMapper.toDto(client);
	}

	@Override
	public ClientDto update(ClientDto clientDto) {
		// Update client
		Client client = findById(clientDto.getId());
		clientRepository.save(ClientMapper.toEntity(client, clientDto));
		return ClientMapper.toDto(client);
	}

	@Override
    public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
        // Partial update account
		Client client = findById(id);
		client.setActive(partialClientDto.isActive());
		clientRepository.save(client);
		return ClientMapper.toDto(client);
    }

	@Override
	public void deleteById(Long id) {
		Client client = findById(id);
		clientRepository.delete(client);
	}

	private Client findById(Long id){
		return clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Client not found for id " + id));
	}
}
