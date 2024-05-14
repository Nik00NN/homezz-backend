package dev.nik00nn.homezzbackend.controller;


import dev.nik00nn.homezzbackend.dto.LoginRequestDTO;
import dev.nik00nn.homezzbackend.dto.LoginResponseDTO;
import dev.nik00nn.homezzbackend.dto.RegisterRequestDTO;
import dev.nik00nn.homezzbackend.dto.CreatedUserDTO;
import dev.nik00nn.homezzbackend.service.authentication.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request){
        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<CreatedUserDTO> register(@RequestBody @Valid RegisterRequestDTO request){
              CreatedUserDTO userCreated = authService.register(request);
              return ResponseEntity.created(URI.create("/api/users/" + userCreated.getId())).body(userCreated);
    }
}
