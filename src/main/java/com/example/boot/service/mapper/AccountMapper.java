package com.example.boot.service.mapper;

import com.example.boot.model.Account;
import com.example.boot.model.Currency;
import com.example.boot.model.dto.AccountRequestDto;
import com.example.boot.model.dto.AccountResponseDto;
import com.example.boot.service.UserService;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountMapper implements DtoMapper<Account, AccountResponseDto>,
        ModelMapper<Account, AccountRequestDto> {
    private final UserService userService;

    @Override
    public AccountResponseDto getDtoFromModel(Account account) {
        return AccountResponseDto.builder()
                .id(account.getId())
                .currency(account.getCurrency())
                .balance(account.getBalance())
                .accountNumber(account.getAccountNumber())
                .isActive(account.isActive())
                .build();
    }

    @Override
    public Account getModelFromDto(AccountRequestDto requestDto) {
        return Account.builder()
                .currency(Currency.valueOf(requestDto.getCurrency().toUpperCase()))
                .accountNumber(requestDto.getAccountNumber())
                .balance(BigDecimal.valueOf(requestDto.getBalance()))
                .user(userService.findById(requestDto.getUserId()))
                .isActive(true)
                .build();
    }
}
