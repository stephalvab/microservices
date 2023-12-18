package com.devsu.msclient.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "client")
@PrimaryKeyJoinColumn(name = "client_id")
public class Client extends Person {
    private String password;
    private boolean status;
}


