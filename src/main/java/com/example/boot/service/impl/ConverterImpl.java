package com.example.boot.service.impl;

import com.example.boot.model.Currency;
import com.example.boot.service.Converter;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConverterImpl implements Converter {
    @Value("${service.url}")
    private String url;
    private final HttpClientService httpClientService;

    public ConverterImpl(HttpClientService httpClientService) {
        this.httpClientService = httpClientService;
    }

    @Override
    public BigDecimal convert(Currency from, Currency to, BigDecimal amount) {
        String urlString = String.format(url + "?from=%s&to=%s&amount=%s", from, to, amount);
        return httpClientService.exchange(urlString);
    }
}
