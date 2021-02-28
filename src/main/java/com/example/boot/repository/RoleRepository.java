package com.example.boot.repository;

import com.example.boot.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    default Optional<Role> getById(Long id) {
        return Optional.of(getOne(id));
    }
}
