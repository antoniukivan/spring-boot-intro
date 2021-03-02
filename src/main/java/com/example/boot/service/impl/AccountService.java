package com.example.boot.service.impl;

import com.example.boot.model.Account;
import java.util.List;

public interface AccountService {
    Account save(Account account);

    List<Account> findAllByUserPhoneNumber(String phoneNumber);

    Account findAllByAccountNumber(String accountNumber);

    void patch(String accountNumber);
}
