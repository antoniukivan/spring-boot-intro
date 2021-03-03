package com.example.boot.service;

import com.example.boot.model.Currency;
import java.math.BigDecimal;

public interface Converter {
    BigDecimal convert(Currency from, Currency to, BigDecimal amount);
}
