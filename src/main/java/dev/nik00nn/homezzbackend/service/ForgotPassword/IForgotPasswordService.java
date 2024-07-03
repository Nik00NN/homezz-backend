package dev.nik00nn.homezzbackend.service.ForgotPassword;

public interface IForgotPasswordService {
    String sendEmail(String to, String subject, String body);
}
