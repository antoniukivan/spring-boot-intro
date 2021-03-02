package com.example.boot.service;

import com.example.boot.model.Account;
import com.example.boot.model.Transaction;
import java.util.List;

public interface TransactionService {
    List<Transaction> getAllByAccount(int page, int size, Account account);

    void transfer(String accountFromNumber, String accountToNumber, double amount);
}
