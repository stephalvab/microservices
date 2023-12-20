package com.devsu.msclient.mocks;


import com.devsu.msclient.model.dto.ClientDto;
import com.devsu.msclient.model.entity.Client;

public class ClientMockitoUtils {

    public static Client clientBd() {
        Client client = new Client();
        client.setClientId(1);
        client.setName("Jose");
        client.setAddress("Av. Venezuela");
        client.setIdentification("11223344");
        client.setAge(30);
        client.setGender("M");
        client.setPhone("987655434");
        client.setStatus(true);
        client.setPassword("1234");
        return client;
    }

    public static Client clientBd2() {
        Client client = new Client();
        client.setClientId(2);
        client.setName("Carla");
        client.setAddress("Av. San Borja");
        client.setIdentification("65425253");
        client.setAge(20);
        client.setGender("F");
        client.setPhone("98555434");
        client.setStatus(true);
        client.setPassword("8844");
        return client;
    }

    public static ClientDto clientDto() {
        return ClientDto.builder()
                .id(1)
                .name("Jose")
                .age(30)
                .address("Av. Venezuela")
                .identification("11223344")
                .gender("M")
                .phone("987655434")
                .status(true)
                .build();
    }

}
