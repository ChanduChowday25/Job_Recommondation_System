package com.jobportal.repository;

import com.jobportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional; // ✅ add this import

public interface UserRepository extends JpaRepository<User, Long> {

    // ✅ ADD THIS METHOD
    Optional<User> findByEmail(String email);
}