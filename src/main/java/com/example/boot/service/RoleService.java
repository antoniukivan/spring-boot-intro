package com.example.boot.service;

import com.example.boot.model.Role;

public interface RoleService {
    Role save(Role role);

    Role getByName(String name);
}
