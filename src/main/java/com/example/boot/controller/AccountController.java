package com.example.boot.controller;

import com.example.boot.model.Account;
import com.example.boot.model.dto.AccountRequestDto;
import com.example.boot.model.dto.AccountResponseDto;
import com.example.boot.model.dto.TransactionRequestDto;
import com.example.boot.model.dto.TransactionResponseDto;
import com.example.boot.service.AccountService;
import com.example.boot.service.TransactionService;
import com.example.boot.service.mapper.AccountMapper;
import com.example.boot.service.mapper.TransactionMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody AccountRequestDto accountRequestDto) {
        Account account = accountMapper.getModelFromDto(accountRequestDto);
        accountService.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/by-phone")
    public ResponseEntity<List<AccountResponseDto>> getAllByPhoneNumber(
            @RequestParam String phoneNumber) {
        List<AccountResponseDto> allByUserPhoneNumber
                = accountService.findAllByUserPhoneNumber(phoneNumber)
                .stream()
                .map(accountMapper::getDtoFromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allByUserPhoneNumber);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<BigDecimal> getBalanceByAccountNumber(
            @PathVariable String accountNumber) {
        Account account = accountService.findByAccountNumber(accountNumber);
        return ResponseEntity.ok(account.getBalance());
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody TransactionRequestDto transactionRequestDto) {
        Account accountFrom
                = accountService.findByAccountNumber(transactionRequestDto.getAccountNumberFrom());
        Account accountTo
                = accountService.findByAccountNumber(transactionRequestDto.getAccountNumberTo());
        BigDecimal amount = BigDecimal.valueOf(transactionRequestDto.getAmount());

        transactionService.transfer(accountFrom, accountTo, amount);

        return ResponseEntity.ok().build();
    }

    @GetMapping("//history/{accountNumber}")
    public ResponseEntity<List<TransactionResponseDto>> getPaymentHistory(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @PathVariable String accountNumber) {

        List<TransactionResponseDto> transactions = transactionService
                .getAllByAccount(page, size, accountService.findByAccountNumber(accountNumber))
                .stream()
                .map(transactionMapper::getDtoFromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactions);
    }

    @PatchMapping("/{accountNumber}")
    public ResponseEntity<AccountResponseDto> accountBlock(@PathVariable String accountNumber) {
        Account account = accountService.findByAccountNumber(accountNumber);
        account.setActive(false);
        accountService.save(account);
        AccountResponseDto responseDto = accountMapper.getDtoFromModel(account);
        return ResponseEntity.ok(responseDto);
    }
}
