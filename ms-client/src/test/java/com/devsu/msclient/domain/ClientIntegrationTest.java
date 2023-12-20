package com.devsu.msclient.domain;

import com.devsu.msclient.mocks.ClientMockitoUtils;
import com.devsu.msclient.model.dto.ClientDto;
import com.devsu.msclient.model.entity.Client;
import com.devsu.msclient.repository.ClientRepository;
import com.devsu.msclient.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ClientIntegrationTest {

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @Test
    public void testGetAllClients() {
        Client client1 = ClientMockitoUtils.clientBd();
        Client client2 = ClientMockitoUtils.clientBd2();
        List<Client> mockClients = Arrays.asList(client1, client2);

        when(clientRepository.findAll()).thenReturn(mockClients);

        Flux<ClientDto> clientDtoFlux = clientService.getAllClients();

        StepVerifier.create(clientDtoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

}
