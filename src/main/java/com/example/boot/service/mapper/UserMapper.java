package com.example.boot.service.mapper;

import com.example.boot.model.User;
import com.example.boot.model.dto.UserRequestDto;
import com.example.boot.model.dto.UserResponseDto;
import com.example.boot.service.RoleService;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements DtoMapper<User, UserResponseDto>,
        ModelMapper<User, UserRequestDto> {

    public static final String ROLE_USER = "USER";
    private final RoleService roleService;

    public UserMapper(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public UserResponseDto getDtoFromModel(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .dateOfBirth(user.getDateOfBirth())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    @Override
    public User getModelFromDto(UserRequestDto requestDto) {
        return User.builder()
                .name(requestDto.getName())
                .dateOfBirth(LocalDate.parse(requestDto.getDateOfBirth()))
                .phoneNumber(requestDto.getPhoneNumber())
                .password(requestDto.getPassword())
                .roles(Set.of(roleService.getByName(ROLE_USER)))
                .build();
    }
}
