package dev.nik00nn.homezzbackend.service.user;

import dev.nik00nn.homezzbackend.domain.User;
import dev.nik00nn.homezzbackend.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    Optional<User> getCurrentUser();
}
