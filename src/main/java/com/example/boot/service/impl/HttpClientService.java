package com.example.boot.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Service;

@Service
public class HttpClientService {
    public BigDecimal exchange(String url) {
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
            throw new RuntimeException("Can't exchange by url: " + url, e);
        }
    }
}
