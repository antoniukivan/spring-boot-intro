package com.example.boot.model.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String phoneNumber;
}
