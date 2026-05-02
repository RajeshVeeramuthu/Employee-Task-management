package com.example.etmsbackend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {

        String subject = "ETMS Account Verification OTP";
        String htmlContent =
                "<div style='font-family:Arial, sans-serif; line-height:1.5;'>" +
            "<h2 style='color:#0b5394; margin-bottom:16px;'>Welcome to ETMS</h2>" +
            "<p>Use the one-time password (OTP) below to complete your account creation.</p>" +
            "<p style='margin:16px 0 8px 0; font-weight:bold;'>Your OTP code:</p>" +
            "<h1 style='color:#0052cc; font-size:28px; letter-spacing:4px; margin:0;'>" + otp + "</h1>" +
            "<p style='margin-top:12px; color:#555;'>This code is valid for 5 minutes.</p>" +
            "<p style='margin-top:8px; font-size:12px; color:#777;'>"
                + "Do not share this code with anyone. If you did not request this, you can safely ignore this email."
            + "</p>" +
        "</div>";

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("rajeshindumuthu03@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email", e);
        }
    }
}
