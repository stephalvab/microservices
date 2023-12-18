package com.devsu.msclient.controller;

import com.devsu.msclient.exception.MessageException;
import com.devsu.msclient.exception.NotFoundException;
import com.devsu.msclient.model.dto.ClientDto;
import com.devsu.msclient.model.dto.ClientSaveDto;
import com.devsu.msclient.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<?> getAllClients() {
        try {
            List<ClientDto> clients = clientService.getAllClients();
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageException.Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable int id) {
        try {
            ClientDto client = clientService.getClientById(id);
            return ResponseEntity.ok(client);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageException.Message(HttpStatus.NOT_FOUND, e.getLocalizedMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageException.Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage()));
        }
    }

    @Validated
    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody @Valid ClientSaveDto clientSaveDto) {
        try {
            ClientDto createdClient = clientService.saveClient(clientSaveDto);
            return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageException.Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage()));
        }
    }

    @Validated
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable int id, @RequestBody @Valid ClientSaveDto clientSaveDto) {
        try {
            ClientDto updatedClient = clientService.updateClient(id, clientSaveDto);
            return new ResponseEntity<>(updatedClient, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageException.Message(HttpStatus.NOT_FOUND, e.getLocalizedMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageException.Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClientById(@PathVariable int id) {
        try {
            clientService.deleteClientById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageException.Message(HttpStatus.NOT_FOUND, e.getLocalizedMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageException.Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage()));
        }
    }
}
