package com.devsu.msaccount.model.entity;

import jakarta.persistence.*;
import lombok.Data;

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

