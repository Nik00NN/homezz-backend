package dev.nik00nn.homezzbackend.dto;


import dev.nik00nn.homezzbackend.domain.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private String username;
    private String emailAddress;
    private String phoneNumber;
    private String address;
    private File profilePicture;
}
