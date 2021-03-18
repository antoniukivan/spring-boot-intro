package com.example.boot.controller;

import com.example.boot.model.User;
import com.example.boot.model.dto.UserRequestDto;
import com.example.boot.model.dto.UserResponseDto;
import com.example.boot.service.UserService;
import com.example.boot.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody UserRequestDto userRequestDto) {
        User user = userMapper.getModelFromDto(userRequestDto);
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody UserRequestDto userRequestDto) {
        User user = userMapper.getModelFromDto(userRequestDto);
        user.setId(id);
        user.setRoles(userService.findById(id).getRoles());
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        UserResponseDto userResponseDto = userMapper.getDtoFromModel(userService.findById(id));
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/by_phone")
    public ResponseEntity<UserResponseDto> getById(@RequestParam String phoneNumber) {
        UserResponseDto userResponseDto = userMapper
                .getDtoFromModel(userService.findByPhoneNumber(phoneNumber));
        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
