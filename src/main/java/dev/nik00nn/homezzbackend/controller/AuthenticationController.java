package dev.nik00nn.homezzbackend.controller;


import dev.nik00nn.homezzbackend.domain.User;
import dev.nik00nn.homezzbackend.domain.UserRole;
import dev.nik00nn.homezzbackend.dto.LoginRequestDTO;
import dev.nik00nn.homezzbackend.dto.LoginResponseDTO;
import dev.nik00nn.homezzbackend.dto.RegisterRequestDTO;
import dev.nik00nn.homezzbackend.dto.UserDTO;
import dev.nik00nn.homezzbackend.repository.IUserRepository;
import dev.nik00nn.homezzbackend.service.authentication.AuthenticationService;
import dev.nik00nn.homezzbackend.service.jwt.JwtService;
import org.hibernate.engine.spi.Resolution;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService tokenService;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(AuthenticationService authService, AuthenticationManager authenticationManager, JwtService tokenService, IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    //todo login endpoint

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request){
        Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenService.generateToken(authentication);
        System.out.println(authentication);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    //todo register endpoint

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO request){
        if(userRepository.existsByUsername( request.getUsername())){
            return ResponseEntity.badRequest().build();
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAddress(request.getAddress());
        user.setEmailAddress(request.getEmailAddress());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPosts(new ArrayList<>());
        user.setRole(UserRole.USER);

        userRepository.save(user);

        return ResponseEntity.ok("User registered success");
    }
}
