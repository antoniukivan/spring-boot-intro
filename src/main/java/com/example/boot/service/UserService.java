package com.example.boot.service;

import com.example.boot.model.User;

public interface UserService {
    User add(User user);

    User findById(Long id);

    void delete(Long id);

    User findByPhoneNumber(String phoneNumber);
}
