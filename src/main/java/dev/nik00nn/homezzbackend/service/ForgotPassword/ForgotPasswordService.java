package dev.nik00nn.homezzbackend.service.ForgotPassword;


import dev.nik00nn.homezzbackend.domain.User;
import dev.nik00nn.homezzbackend.repository.IUserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordService implements IForgotPasswordService {

    private final JavaMailSender javaMailSender;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ForgotPasswordService(JavaMailSender javaMailSender, IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String sendEmail(String to, String subject, String body) {
        Optional<User> byEmailAddress = userRepository.findByEmailAddress(to);
        String token = generateSecretToken(to);
        if (byEmailAddress.isPresent()) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body + token);
            javaMailSender.send(message);
            return token;
        }
        return null;
    }

    private String generateSecretToken(String email) {
        return passwordEncoder.encode(email);
    }
}
