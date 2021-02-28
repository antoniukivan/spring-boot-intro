package com.example.boot.repository;

import com.example.boot.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.phoneNumber = ?1")
    Optional<User> findByPhoneNumber(String phoneNumber);
}
