package com.devsu.hackerearth.backend.client.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;

public class ClientMapper {

    // Convertir de Client a ClientDto
    public static ClientDto toDto(Client client) {
        if (client == null) {
            return null;
        }
        return new ClientDto(
            client.getId(),
            client.getDni(),
            client.getName(),
            client.getPassword(),
            client.getGender(),
            client.getAge(),
            client.getAddress(),
            client.getPhone(),
            client.isActive()
        );
    }

    // Convertir de ClientDto a Client
    public static Client toEntity(ClientDto clientDto) {
        if (clientDto == null) {
            return null;
        }
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setDni(clientDto.getDni());
        client.setName(clientDto.getName());
        client.setPassword(clientDto.getPassword());
        client.setGender(clientDto.getGender());
        client.setAge(clientDto.getAge());
        client.setAddress(clientDto.getAddress());
        client.setPhone(clientDto.getPhone());
        client.setActive(clientDto.isActive());
        return client;
    }

    // Convertir lista de Client a lista de ClientDto
    public static List<ClientDto> toDto(List<Client> clients) {
        return clients.stream()
            .map(ClientMapper::toDto)
            .collect(Collectors.toList());
    }

    // Convertir lista de ClientDto a lista de Client
    public static List<Client> toEntity(List<ClientDto> clientDtos) {
        return clientDtos.stream()
            .map(ClientMapper::toEntity)
            .collect(Collectors.toList());
    }
}
