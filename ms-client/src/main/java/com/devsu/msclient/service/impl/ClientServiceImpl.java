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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Flux<ClientDto> getAllClients() {
        return Flux.fromStream(clientRepository.findAll().stream()
                .map(this::builderClientDto));
    }

    @Override
    public Mono<ClientDto> getClientById(int id) {
        return Mono.justOrEmpty(clientRepository.findById(id)
                        .map(this::builderClientDto))
                .switchIfEmpty(Mono.error(new NotFoundException(Constants.CLIENT_NOT_FOUND)));
    }

    @Override
    public Mono<ClientDto> saveClient(ClientSaveDto clientDto) {
        Client client = new Client();
        client.setStatus(true);
        return Mono.just(builderClientDto(clientRepository
                .save(builderClientBd(clientDto, client))));
    }

    @Override
    public Mono<ClientDto> updateClient(int id, ClientSaveDto clientSaveDto) {
        Client clientBd = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.CLIENT_NOT_FOUND));
        clientBd.setStatus(clientSaveDto.isStatus());
        return Mono.just(builderClientDto(clientRepository
                .save(builderClientBd(clientSaveDto, clientBd))));
    }

    @Override
    public Mono<Void> deleteClientById(int id) {
        try {
            clientRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(Constants.CLIENT_NOT_FOUND);
        }
        return Mono.empty();
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
