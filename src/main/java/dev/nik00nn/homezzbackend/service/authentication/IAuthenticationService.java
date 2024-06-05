package dev.nik00nn.homezzbackend.service.authentication;

import dev.nik00nn.homezzbackend.dto.authentication.LoginRequestDTO;
import dev.nik00nn.homezzbackend.dto.authentication.LoginResponseDTO;
import dev.nik00nn.homezzbackend.dto.authentication.RegisterRequestDTO;
import dev.nik00nn.homezzbackend.dto.CreatedUserDTO;

public interface IAuthenticationService {
    CreatedUserDTO register(RegisterRequestDTO request);
    LoginResponseDTO login(LoginRequestDTO request);
}
