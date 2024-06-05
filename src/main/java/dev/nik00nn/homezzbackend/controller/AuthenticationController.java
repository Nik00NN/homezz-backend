package dev.nik00nn.homezzbackend.controller;

import dev.nik00nn.homezzbackend.dto.authentication.LoginRequestDTO;
import dev.nik00nn.homezzbackend.dto.authentication.LoginResponseDTO;
import dev.nik00nn.homezzbackend.dto.authentication.RegisterRequestDTO;
import dev.nik00nn.homezzbackend.dto.CreatedUserDTO;
import dev.nik00nn.homezzbackend.service.authentication.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request){
        LoginResponseDTO response = authService.login(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        System.out.println(principal);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<CreatedUserDTO> register(@RequestBody @Valid RegisterRequestDTO request){
        CreatedUserDTO userCreated = authService.register(request);
        return ResponseEntity.created(URI.create("/api/users/" + userCreated.getId())).body(userCreated);
    }
}
