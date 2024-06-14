package dev.nik00nn.homezzbackend.controller;

import dev.nik00nn.homezzbackend.dto.UserDTO;
import dev.nik00nn.homezzbackend.dto.authentication.LoginRequestDTO;
import dev.nik00nn.homezzbackend.dto.authentication.LoginResponseDTO;
import dev.nik00nn.homezzbackend.dto.authentication.RegisterRequestDTO;
import dev.nik00nn.homezzbackend.service.authentication.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Validated
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request){
        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/register",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> register(@Valid @RequestPart RegisterRequestDTO request, @RequestParam("profilePhoto") MultipartFile file) throws IOException {
        UserDTO createdUser = authService.register(request, file);
        return ResponseEntity.ok().body(createdUser);
    }
}
