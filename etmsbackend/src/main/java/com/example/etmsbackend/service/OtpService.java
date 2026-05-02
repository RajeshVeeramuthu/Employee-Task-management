package com.example.etmsbackend.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    private final Map<String, String> otpCache = new HashMap<>();

    public String generateOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999)); // 6-digit OTP
        otpCache.put(email, otp);
        return otp;
    }

    public boolean validateOtp(String email, String enteredOtp) {
        String correctOtp = otpCache.get(email);
        return correctOtp != null && correctOtp.equals(enteredOtp);
    }
}
