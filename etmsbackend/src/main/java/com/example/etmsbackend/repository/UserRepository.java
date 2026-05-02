package com.example.etmsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.etmsbackend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByWorkId(String workId);
    List<User> findByRole(String role);
     boolean existsByEmail(String email);

     
}

