package dev.nik00nn.homezzbackend.dto.authentication;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";

    public LoginResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
