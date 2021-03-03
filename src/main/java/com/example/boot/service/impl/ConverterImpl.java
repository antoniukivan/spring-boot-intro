package com.example.boot.service.impl;

import com.example.boot.model.Currency;
import com.example.boot.service.Converter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConverterImpl implements Converter {
    @Value("${service.url}")
    private String url;

    @Override
    public BigDecimal convert(Currency from, Currency to, BigDecimal amount) {
        url = url + "?from=" + from + "&to=" + to + "&amount=" + amount;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        try {
            HttpResponse<String> response
                    = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNodeRoot = objectMapper.readTree(response.body());
            JsonNode jsonResult = jsonNodeRoot.get("result");
            return new BigDecimal(jsonResult.asText());
        } catch (Exception e) {
            throw new RuntimeException("Can't convert from: " + from + " to: " + to, e);
        }
    }
}
