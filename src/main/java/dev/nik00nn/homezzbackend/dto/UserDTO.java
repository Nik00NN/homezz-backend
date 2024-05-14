package dev.nik00nn.homezzbackend.dto;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private String emailAddress;
    private String phoneNumber;
    private String address;
}
