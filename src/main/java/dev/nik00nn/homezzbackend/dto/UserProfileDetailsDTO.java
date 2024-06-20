package dev.nik00nn.homezzbackend.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDetailsDTO {
    @NotBlank
    private String emailAddress;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String address;
}
