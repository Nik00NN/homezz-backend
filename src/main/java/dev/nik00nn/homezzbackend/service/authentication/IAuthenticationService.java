package dev.nik00nn.homezzbackend.service.authentication;

import dev.nik00nn.homezzbackend.dto.LoginRequestDTO;
import dev.nik00nn.homezzbackend.dto.LoginResponseDTO;
import dev.nik00nn.homezzbackend.dto.RegisterRequestDTO;
import dev.nik00nn.homezzbackend.dto.CreatedUserDTO;

public interface IAuthenticationService {
    CreatedUserDTO register(RegisterRequestDTO request);
    LoginResponseDTO login(LoginRequestDTO request);
}
