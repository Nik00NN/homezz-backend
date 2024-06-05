package dev.nik00nn.homezzbackend.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegisterRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    @Length(min = 8)
    private String password;

    @NotBlank
    @Email
    private String emailAddress;

    @NotBlank
    private String address;

    @NotBlank
    private String phoneNumber;
}
