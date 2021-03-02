package com.example.boot.service;

import com.example.boot.model.Account;
import com.example.boot.model.Transaction;
import java.util.List;

public interface TransactionService {
    List<Transaction> findAllByAccount(Account account);

    void transfer(String accountFromNumber, String accountToNumber, double amount);
}