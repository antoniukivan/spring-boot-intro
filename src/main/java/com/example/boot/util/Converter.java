package com.example.boot.util;

import com.example.boot.model.Currency;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

public class Converter {
    private static final String URL_STRING = "https://api.exchangerate.host/convert";

    public static BigDecimal convert(BigDecimal amount, Currency from, Currency to) {
        StringBuilder stringBuilder = new StringBuilder(URL_STRING)
                .append("?from=").append(from)
                .append("&to=").append(to)
                .append("&amount=").append(amount);

        try {
            URL url = new URL(stringBuilder.toString());
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonObj = root.getAsJsonObject();
            String reqResult = jsonObj.get("result").getAsString();
            return new BigDecimal(reqResult);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't convert from: " + from + " to: " + to, e);
        }
    }
}
