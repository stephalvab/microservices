package com.devsu.msclient.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientSaveDto {
    @NotNull(message = "El campo 'name' no puede estar vacío")
    private String name;
//    @Size(min = 1, max = 1)
    private String gender;
    private int age;
    @NotNull(message = "El campo 'identification' no puede estar vacío")
    private String identification;
    private String address;
    private String phone;
    @NotNull(message = "El campo 'password' no puede estar vacío")
    private String password;
    private boolean status;
}
