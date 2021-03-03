package com.example.boot.model.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class TransactionRequestDto {
    @NotEmpty
    private String fromAccount;
    @NotEmpty
    private String toAccount;
    @Min(0)
    private BigDecimal amount;
}
