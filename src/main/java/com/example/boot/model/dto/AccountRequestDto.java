package com.example.boot.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountRequestDto {
    private String accountNumber;
    private Long userId;
    private String currency;
    private double balance;
}
