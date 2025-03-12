package com.devsu.hackerearth.backend.account.service;

import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.account.model.dto.ClientDto;

public interface ClientService {
    public ResponseEntity<ClientDto> getClientById(Long clientId);
}
