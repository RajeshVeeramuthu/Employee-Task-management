package com.example.etmsbackend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adminEmail;
    private String employeeEmail;

    private String title;

    @Column(length = 2000)
    private String description;

    private LocalDate assignedDate;
    private LocalDate dueDate;

    private String status;  

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] fileAssigned;

    private String fileAssignedName;
    private String fileAssignedType;
    private Long fileAssignedSize;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] fileCompleted;

    private String fileCompletedName;
    private String fileCompletedType;
    private Long fileCompletedSize;

    private String taskStatus;  // 'a' = alive, 'd' = deleted

    @Transient // Important: tell JPA to ignore this field
private String fileCompletedBase64;

    // -------------------------
    // Getters & Setters
    // -------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getFileAssigned() {
        return fileAssigned;
    }

    public void setFileAssigned(byte[] fileAssigned) {
        this.fileAssigned = fileAssigned;
    }

    public String getFileAssignedName() {
        return fileAssignedName;
    }

    public void setFileAssignedName(String fileAssignedName) {
        this.fileAssignedName = fileAssignedName;
    }

    public String getFileAssignedType() {
        return fileAssignedType;
    }

    public void setFileAssignedType(String fileAssignedType) {
        this.fileAssignedType = fileAssignedType;
    }

    public Long getFileAssignedSize() {
        return fileAssignedSize;
    }

    public void setFileAssignedSize(Long fileAssignedSize) {
        this.fileAssignedSize = fileAssignedSize;
    }

    public byte[] getFileCompleted() {
        return fileCompleted;
    }

    public void setFileCompleted(byte[] fileCompleted) {
        this.fileCompleted = fileCompleted;
    }

    public String getFileCompletedName() {
        return fileCompletedName;
    }

    public void setFileCompletedName(String fileCompletedName) {
        this.fileCompletedName = fileCompletedName;
    }

    public String getFileCompletedType() {
        return fileCompletedType;
    }

    public void setFileCompletedType(String fileCompletedType) {
        this.fileCompletedType = fileCompletedType;
    }

    public Long getFileCompletedSize() {
        return fileCompletedSize;
    }

    public void setFileCompletedSize(Long fileCompletedSize) {
        this.fileCompletedSize = fileCompletedSize;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getFileCompletedBase64() {
    return fileCompletedBase64;
}

public void setFileCompletedBase64(String fileCompletedBase64) {
    this.fileCompletedBase64 = fileCompletedBase64;
}
}
