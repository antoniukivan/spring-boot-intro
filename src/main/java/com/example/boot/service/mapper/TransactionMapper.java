package com.example.boot.service.mapper;

import com.example.boot.model.Transaction;
import com.example.boot.model.dto.TransactionResponseDto;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper implements DtoMapper<Transaction, TransactionResponseDto> {

    @Override
    public TransactionResponseDto getDtoFromModel(Transaction transaction) {
        return TransactionResponseDto.builder()
                .id(transaction.getId())
                .accountNumberTo(transaction.getAccountTo().getAccountNumber())
                .accountNumberFrom(transaction.getAccountFrom().getAccountNumber())
                .amount(transaction.getAmount())
                .date(transaction.getDate())
                .build();
    }
}
