package com.example.boot.repository;

import com.example.boot.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    default Optional<User> getById(Long id) {
        return Optional.of(getOne(id));
    }
}
