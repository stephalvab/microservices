package com.devsu.msclient.domain;

import com.devsu.msclient.mocks.ClientMockitoUtils;
import com.devsu.msclient.model.dto.ClientDto;
import com.devsu.msclient.model.entity.Client;
import com.devsu.msclient.repository.ClientRepository;
import com.devsu.msclient.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class ClientTest {
    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetClientById() {
        Client client = ClientMockitoUtils.clientBd();
        ClientDto expectedClient = ClientMockitoUtils.clientDto();
        Mockito.when(clientRepository.findById(client.getClientId())).thenReturn(Optional.of(client));
        Mono<ClientDto> clientDtoMono = clientService.getClientById(client.getClientId());
        ClientDto clientDto = clientDtoMono.block();
        Assertions.assertNotNull(clientDto);
        Assertions.assertEquals(expectedClient.getId(), clientDto.getId());
        Assertions.assertEquals(expectedClient.getName(), clientDto.getName());
        Assertions.assertEquals(expectedClient.getAge(), clientDto.getAge());
        Assertions.assertEquals(expectedClient.getGender(), clientDto.getGender());
        Assertions.assertEquals(expectedClient.getAddress(), clientDto.getAddress());
        Assertions.assertEquals(expectedClient.getPhone(), clientDto.getPhone());
        Assertions.assertEquals(expectedClient.getIdentification(), clientDto.getIdentification());
        Assertions.assertEquals(expectedClient.isStatus(), clientDto.isStatus());
    }
}

