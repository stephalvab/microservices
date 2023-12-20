package com.devsu.msclient.service;

import com.devsu.msclient.model.dto.ClientDto;
import com.devsu.msclient.model.dto.ClientSaveDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ClientService {
    Flux<ClientDto> getAllClients();

    Mono<ClientDto> getClientById(int id);

    Mono<ClientDto> saveClient(ClientSaveDto clientSaveDto);

    Mono<ClientDto> updateClient(int id, ClientSaveDto clientUpdateDto);

    Mono<Void> deleteClientById(int id);
}
