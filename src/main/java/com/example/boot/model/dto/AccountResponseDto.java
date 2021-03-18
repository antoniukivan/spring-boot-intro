package com.example.boot.model.dto;

import com.example.boot.model.Currency;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponseDto {
    private Long id;
    private String accountNumber;
    private Currency currency;
    private BigDecimal balance;
    private boolean isActive;
    private Long userId;
}
