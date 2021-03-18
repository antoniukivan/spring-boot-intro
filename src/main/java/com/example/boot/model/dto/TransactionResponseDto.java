package com.example.boot.model.dto;

import com.example.boot.model.Transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionResponseDto {
    private Long id;
    private String accountNumberFrom;
    private String accountNumberTo;
    private BigDecimal amount;
    private LocalDateTime date;
    private Transaction.Type type;
}
