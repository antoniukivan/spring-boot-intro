package com.example.boot.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionRequestDto {
    private String accountNumberFrom;
    private String accountNumberTo;
    private double amount;
}
