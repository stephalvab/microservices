package com.devsu.msclient.service.impl;

import com.devsu.msclient.exception.NotFoundException;
import com.devsu.msclient.model.dto.ClientDto;
import com.devsu.msclient.model.dto.ClientSaveDto;
import com.devsu.msclient.model.entity.Client;
import com.devsu.msclient.repository.ClientRepository;
import com.devsu.msclient.service.ClientService;
import com.devsu.msclient.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::builderClientDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto getClientById(int id) {
        return clientRepository.findById(id)
                .map(this::builderClientDto)
                .orElseThrow(() -> new NotFoundException(Constants.CLIENT_NOT_FOUND));
    }

    @Override
    public ClientDto saveClient(ClientSaveDto clientDto) {
        Client client = new Client();
        client.setStatus(Constants.ACTIVO);
        return builderClientDto(clientRepository
                .save(builderClientBd(clientDto, client)));
    }

    @Override
    public ClientDto updateClient(int id, ClientSaveDto clientSaveDto) {
        Client clientBd = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.CLIENT_NOT_FOUND));
        clientBd.setStatus(clientSaveDto.isStatus());
        return builderClientDto(clientRepository
                .save(builderClientBd(clientSaveDto, clientBd)));
    }

    @Override
    public void deleteClientById(int id) {
        try {
            clientRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(Constants.CLIENT_NOT_FOUND);
        }
    }

    private ClientDto builderClientDto(Client client) {
        return ClientDto.builder()
                .id(client.getClientId())
                .name(client.getName())
                .age(client.getAge())
                .gender(client.getGender())
                .address(client.getAddress())
                .phone(client.getPhone())
                .identification(client.getIdentification())
                .status(client.isStatus())
                .build();
    }

    private Client builderClientBd(ClientSaveDto clientSaveDto, Client client) {
        client.setName(clientSaveDto.getName());
        client.setPassword(clientSaveDto.getPassword()); //
        client.setAddress(clientSaveDto.getAddress());
        client.setPhone(clientSaveDto.getPhone());
        client.setAge(clientSaveDto.getAge());
        client.setGender(clientSaveDto.getGender());
        client.setIdentification(clientSaveDto.getIdentification());
        return client;
    }
}
