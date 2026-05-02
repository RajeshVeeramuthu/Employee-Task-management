package com.example.etmsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.etmsbackend.model.Task;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByEmployeeEmail(String email);
    List<Task> findByTaskStatus(String taskStatus);

}
