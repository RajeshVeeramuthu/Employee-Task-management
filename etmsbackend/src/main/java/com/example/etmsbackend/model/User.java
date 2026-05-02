package com.example.etmsbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    // ---------- ID ----------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ---------- WORK ID ----------
    @NotBlank(message = "Work ID is required")
    @Column(nullable = false, unique = true)
    private String workId;

    // ---------- NAME ----------
    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    // ---------- INITIAL ----------
    @NotBlank(message = "Initial is required")
    private String initial;

    // ---------- MOBILE ----------
    @NotBlank(message = "Mobile Number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number must be 10 digits")
    @Column(nullable = false)
    private String mobileNo;

    // ---------- DOB ----------
    @NotNull(message = "Date of Birth is required")
    @Column(nullable = false)
    private LocalDate dob;

    // ---------- EMAIL ----------
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    // ---------- PASSWORD ----------
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Column(nullable = false)
    private String password;

    // ---------- ROLE ----------
    @NotBlank(message = "Role is required")
    @Column(nullable = false)
    private String role;   // ADMIN or EMP

    // ---------- JOB POSITION ----------
    @NotBlank(message = "Job Position is required")
    private String jobPosition;



    // ---------- GETTERS & SETTERS ----------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getWorkId() { return workId; }
    public void setWorkId(String workId) { this.workId = workId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getInitial() { return initial; }
    public void setInitial(String initial) { this.initial = initial; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getJobPosition() { return jobPosition; }
    public void setJobPosition(String jobPosition) { this.jobPosition = jobPosition; }
}
