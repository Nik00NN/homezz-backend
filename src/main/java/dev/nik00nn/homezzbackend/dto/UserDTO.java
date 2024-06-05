package dev.nik00nn.homezzbackend.dto;

import dev.nik00nn.homezzbackend.domain.File;
import dev.nik00nn.homezzbackend.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String emailAddress;
    private String phoneNumber;
    private String address;
    private File profilePicture;
    private List<Post> posts;
}
