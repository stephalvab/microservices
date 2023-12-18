package com.devsu.msclient.service;

import com.devsu.msclient.model.dto.ClientDto;
import com.devsu.msclient.model.dto.ClientSaveDto;

import java.util.List;

public interface ClientService {
    List<ClientDto> getAllClients();

    ClientDto getClientById(int id);

    ClientDto saveClient(ClientSaveDto clientSaveDto);

    ClientDto updateClient(int id, ClientSaveDto clientUpdateDto);

    void deleteClientById(int id);
}
