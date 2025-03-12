package com.devsu.hackerearth.backend.account.service;

import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.dto.ClientDto;


@Service
public class ClientServiceImpl implements ClientService {
    private final RestTemplate restTemplate = new RestTemplate();

    public ClientDto getClientById(Long clientId){
        try {
           return restTemplate.getForEntity("http://localhost:8081/api/clients/"+clientId, ClientDto.class).getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
