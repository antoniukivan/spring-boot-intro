package com.example.boot.service;

import com.example.boot.model.Account;
import com.example.boot.model.Transaction;
import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    List<Transaction> getAllByAccount(int page, int size, Account account);

    void transfer(String accountFromNumber, String accountToNumber, BigDecimal amount);
}
