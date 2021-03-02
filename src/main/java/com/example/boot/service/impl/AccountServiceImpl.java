package com.example.boot.service.impl;

import com.example.boot.model.Account;
import com.example.boot.repository.AccountRepository;
import com.example.boot.service.AccountService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> findAllByUserPhoneNumber(String phoneNumber) {
        return accountRepository.findAllByUserPhoneNumber(phoneNumber);
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).get();
    }

    @Override
    public void patch(String accountNumber) {
        Account account = findByAccountNumber(accountNumber);
        account.setActive(false);
        accountRepository.save(account);
    }
}
