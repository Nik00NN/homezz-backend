package dev.nik00nn.homezzbackend.service.user;

import dev.nik00nn.homezzbackend.domain.User;
import dev.nik00nn.homezzbackend.dto.CreatePostDTO;
import dev.nik00nn.homezzbackend.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> getCurrentUser();
    UserDTO getUserByUsername(String username);
    UserDTO addPost(String username, CreatePostDTO createPostRequest, List<MultipartFile> photos) throws IOException;
}
