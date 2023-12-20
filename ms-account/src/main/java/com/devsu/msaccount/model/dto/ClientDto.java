package com.devsu.msaccount.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDto {
    private int id;
    private String name;
    private String gender;
    private int age;
    private String identification;
    private String address;
    private String phone;
    private boolean status;
}
