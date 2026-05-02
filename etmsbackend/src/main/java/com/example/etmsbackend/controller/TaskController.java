package com.example.etmsbackend.controller;

import com.example.etmsbackend.model.Task;
import com.example.etmsbackend.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {

    private final TaskRepository repo;

    public TaskController(TaskRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Task> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Task create(@RequestBody Task incoming) {

        // Convert Base64 to bytes (Admin upload)
        if (incoming.getFileAssigned() != null && incoming.getFileAssigned().length == 0 && incoming.getFileAssignedName() != null) {
            incoming.setFileAssigned(Base64.getDecoder().decode(incoming.getFileAssignedName()));
        }

        incoming.setAssignedDate(java.time.LocalDate.now());

        return repo.save(incoming);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task incoming) {

        Optional<Task> existingOpt = repo.findById(id);
        if (existingOpt.isEmpty()) return null;

        Task existing = existingOpt.get();

        existing.setTitle(incoming.getTitle());
        existing.setDescription(incoming.getDescription());
        existing.setStatus(incoming.getStatus());
        existing.setDueDate(incoming.getDueDate());
        existing.setEmployeeEmail(incoming.getEmployeeEmail());
        existing.setTaskStatus(incoming.getTaskStatus());

        // Only update fileAssigned when provided
        if (incoming.getFileAssigned() != null && incoming.getFileAssignedName() != null) {
            existing.setFileAssigned(Base64.getDecoder().decode(incoming.getFileAssigned()));
            existing.setFileAssignedName(incoming.getFileAssignedName());
            existing.setFileAssignedType(incoming.getFileAssignedType());
            existing.setFileAssignedSize(incoming.getFileAssignedSize());
        }

        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

@PutMapping("/employee-upload/{id}")
public Task employeeUpload(@PathVariable Long id, @RequestBody Task incoming) {

    Task existing = repo.findById(id).orElseThrow();

    // Correct base64 field name
    if (incoming.getFileCompletedBase64() != null && !incoming.getFileCompletedBase64().isEmpty()) {

        byte[] decoded = Base64.getDecoder().decode(incoming.getFileCompletedBase64());
        existing.setFileCompleted(decoded);

        existing.setFileCompletedName(incoming.getFileCompletedName());
        existing.setFileCompletedType(incoming.getFileCompletedType());
        existing.setFileCompletedSize(incoming.getFileCompletedSize());

        // OPTIONAL: Auto-set status to completed
        existing.setStatus("COMPLETED");
    }

    return repo.save(existing);
}

@PutMapping("/{id}/status")
public Task updateStatus(@PathVariable Long id, @RequestBody Task incoming) {
    Task existing = repo.findById(id).orElseThrow();
    if (incoming.getStatus() != null && !incoming.getStatus().isEmpty()) {
        existing.setStatus(incoming.getStatus());
    }
    return repo.save(existing);
}


}
