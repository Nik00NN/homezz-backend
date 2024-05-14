package dev.nik00nn.homezzbackend.service.jwt;

import org.springframework.security.core.Authentication;

public interface IJwtService {
    String extractUsername(String token);
    String generateToken (Authentication authentication);
    boolean isTokenValid(String token);
}
