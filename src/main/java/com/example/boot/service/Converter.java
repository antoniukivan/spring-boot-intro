package com.example.boot.service;

import com.example.boot.model.Account;
import java.math.BigDecimal;

public interface Converter {
    BigDecimal convert(Account accountFrom, Account accountTo, BigDecimal amount);
}
