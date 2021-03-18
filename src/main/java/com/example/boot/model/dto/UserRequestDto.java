package com.example.boot.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDto {
    private String name;
    private String dateOfBirth;
    private String phoneNumber;
    private String password;
}
