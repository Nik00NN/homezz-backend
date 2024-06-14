package dev.nik00nn.homezzbackend.service.authentication;

import dev.nik00nn.homezzbackend.domain.*;
import dev.nik00nn.homezzbackend.dto.UserDTO;
import dev.nik00nn.homezzbackend.dto.authentication.LoginRequestDTO;
import dev.nik00nn.homezzbackend.dto.authentication.LoginResponseDTO;
import dev.nik00nn.homezzbackend.dto.authentication.RegisterRequestDTO;
import dev.nik00nn.homezzbackend.exception.BadRequestException;
import dev.nik00nn.homezzbackend.repository.IUserRepository;
import dev.nik00nn.homezzbackend.service.file.FileService;
import dev.nik00nn.homezzbackend.service.jwt.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService tokenService;
    private final AuthenticationManager authenticationManager;
    private final FileService fileService;

    public AuthenticationService(IUserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, JwtService tokenService, AuthenticationManager authenticationManager, FileService fileService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.fileService = fileService;
    }

    @Override
    public UserDTO register(RegisterRequestDTO request, MultipartFile photo) throws IOException {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new BadRequestException("Username already exists");

        ProfilePhoto profilePhoto = fileService.saveProfilePhoto(photo);

        User user = User.builder().username(request.getUsername()).password(passwordEncoder.encode(request.getPassword())).emailAddress(request.getEmailAddress())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .posts(new ArrayList<>())
                .role(UserRole.USER)
                .accountType(AccountType.NORMAL_USER)
                .build();

        if (profilePhoto != null) user.setProfilePicture(profilePhoto);
        User createdUser = userRepository.save(user);
        return modelMapper.map(createdUser, UserDTO.class);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenService.generateToken(authentication);
        return new LoginResponseDTO(token);
    }
}
