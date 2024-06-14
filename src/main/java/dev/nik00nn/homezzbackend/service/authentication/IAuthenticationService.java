package dev.nik00nn.homezzbackend.service.authentication;

import dev.nik00nn.homezzbackend.dto.UserDTO;
import dev.nik00nn.homezzbackend.dto.authentication.LoginRequestDTO;
import dev.nik00nn.homezzbackend.dto.authentication.LoginResponseDTO;
import dev.nik00nn.homezzbackend.dto.authentication.RegisterRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IAuthenticationService {
    UserDTO register(RegisterRequestDTO request, MultipartFile photo) throws IOException;
    LoginResponseDTO login(LoginRequestDTO request);
}
