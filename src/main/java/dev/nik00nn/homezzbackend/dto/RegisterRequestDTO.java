package dev.nik00nn.homezzbackend.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String emailAddress;
    private String address;
    private String phoneNumber;
}
