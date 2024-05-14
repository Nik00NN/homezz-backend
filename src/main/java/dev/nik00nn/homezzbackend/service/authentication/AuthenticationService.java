package dev.nik00nn.homezzbackend.service.authentication;

import dev.nik00nn.homezzbackend.domain.User;
import dev.nik00nn.homezzbackend.domain.UserRole;
import dev.nik00nn.homezzbackend.dto.LoginRequestDTO;
import dev.nik00nn.homezzbackend.dto.LoginResponseDTO;
import dev.nik00nn.homezzbackend.dto.RegisterRequestDTO;
import dev.nik00nn.homezzbackend.dto.CreatedUserDTO;
import dev.nik00nn.homezzbackend.exception.BadRequestException;
import dev.nik00nn.homezzbackend.repository.IUserRepository;
import dev.nik00nn.homezzbackend.service.jwt.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements IAuthenticationService{

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(IUserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, JwtService tokenService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public CreatedUserDTO register(RegisterRequestDTO request) {
        if(userRepository.existsByUsername(request.getUsername())) throw new BadRequestException("Username already exists");

        User user = User.builder().username(request.getUsername()).password(passwordEncoder.encode(request.getPassword())).emailAddress(request.getEmailAddress())
                        .phoneNumber(request.getPhoneNumber())
                        .address(request.getAddress())
                        .posts(new ArrayList<>())
                        .role(UserRole.USER).build();

        userRepository.save(user);

        return modelMapper.map(user, CreatedUserDTO.class);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenService.generateToken(authentication);
        return new LoginResponseDTO(token);
    }
}
