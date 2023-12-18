package com.devsu.msclient.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientId;

    private String name;
    private String gender;
    private int age;

    @Column(unique = true)
    private String identification;

    private String address;
    private String phone;
}


