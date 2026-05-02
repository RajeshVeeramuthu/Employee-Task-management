package com.example.etmsbackend.controller;

import com.example.etmsbackend.model.User;
import com.example.etmsbackend.repository.UserRepository;
import com.example.etmsbackend.security.JwtUtil;
import com.example.etmsbackend.service.EmailService;
import com.example.etmsbackend.service.OtpService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final OtpService otpService;

    public AuthController(UserRepository userRepo,
                          JwtUtil jwtUtil,
                          EmailService emailService,
                          OtpService otpService) {

        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
        this.otpService = otpService;
    }

    // -------------------------
    // SEND OTP
    // -------------------------
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String email) {

        if (userRepo.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Email already exists"
            ));
        }

        String otp = otpService.generateOtp(email);
        emailService.sendOtpEmail(email, otp);

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "OTP sent successfully"
        ));
    }

    // -------------------------
    // VERIFY OTP
    // -------------------------
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email,
                                       @RequestParam String otp) {

        boolean valid = otpService.validateOtp(email, otp);

        if (!valid) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Invalid OTP"
            ));
        }

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "OTP verified"
        ));
    }

    // -------------------------
    // REGISTER USER
    // -------------------------
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result) {

        if (result.hasErrors()) {
            String msg = result.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", msg
            ));
        }

        if (userRepo.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Email already exists"
            ));
        }

        userRepo.save(user);

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Account created successfully!"
        ));
    }

    // -------------------------
    // GET login (avoid whitelabel)
    // -------------------------
    @GetMapping("/login")
    public String loginGet() {
        return "Login must be POST";
    }

    // -------------------------
    // LOGIN USER
    // -------------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req) {

        String email = req.get("email");
        String password = req.get("password");

        var opt = userRepo.findByEmail(email);

        if (opt.isEmpty() || !opt.get().getPassword().equals(password)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "status", "error",
                            "message", "Invalid Email or Password"
                    ));
        }

        User u = opt.get();

        String token = jwtUtil.generateToken(
                u.getEmail(),
                Map.of(
                        "role", "ROLE_" + u.getRole(),
                        "email", u.getEmail(),
                        "name", u.getName()
                )
        );

        return ResponseEntity.ok(
                Map.of(
                        "status", "success",
                        "token", token,
                        "user", Map.of(
                                "id", u.getId(),
                                "name", u.getName(),
                                "email", u.getEmail(),
                                "role", u.getRole(),
                                "initial",u.getInitial()
                        )
                )
        );
    }

    // -------------------------
    // GET EMPLOYEES
    // -------------------------
    @GetMapping("/employees")
    public List<User> getEmployees() {
        return userRepo.findByRole("EMP");
    }
}
