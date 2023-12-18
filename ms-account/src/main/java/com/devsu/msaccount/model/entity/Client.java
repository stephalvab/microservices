package com.devsu.msaccount.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "client")
@PrimaryKeyJoinColumn(name = "client_id")
public class Client extends Person {
    private String password;
    private boolean status;

}
