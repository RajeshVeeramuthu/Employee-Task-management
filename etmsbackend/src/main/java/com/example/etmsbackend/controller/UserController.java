package com.example.etmsbackend.controller;

import com.example.etmsbackend.model.User;
import com.example.etmsbackend.repository.UserRepository;

import java.util.List;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // Get all employees (existing)
    @GetMapping("/employees")
    public List<User> getEmployees() {
        return userRepo.findByRole("EMP");
    }

   // ✅ Get logged-in user's profile by email
 @GetMapping("/{email}")
    public User getUserProfile(@PathVariable String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

   @PutMapping("/update")
    public User updateProfile(@RequestBody User updatedUser) {

        // find existing user
        User user = userRepo.findByEmail(updatedUser.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ❌ NOT allowed to update
        // user.setWorkId(...)
        // user.setRole(...)
        // user.setPassword(...)

        // ✅ Allowed fields
        user.setName(updatedUser.getName());
        user.setInitial(updatedUser.getInitial());
        user.setDob(updatedUser.getDob());
        user.setMobileNo(updatedUser.getMobileNo());
        user.setJobPosition(updatedUser.getJobPosition());

        return userRepo.save(user);
    }
    


}
