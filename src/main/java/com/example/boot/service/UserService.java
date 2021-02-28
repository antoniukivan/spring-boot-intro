package com.example.boot.service;

import com.example.boot.model.User;
import java.util.Optional;

public interface UserService {
    User add(User user);

    Optional<User> getById(Long id);
}
