package com.example.boot.service.impl;

import com.example.boot.model.Account;
import com.example.boot.model.Currency;
import com.example.boot.model.Transaction;
import com.example.boot.repository.AccountRepository;
import com.example.boot.repository.TransactionRepository;
import com.example.boot.service.TransactionService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<Transaction> getAllByAccount(int page, int size, Account account) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return transactionRepository.findAllByAccount(account, pageable);

    }

    @Override
    @Transactional
    public void transfer(String accountFromNumber, String accountToNumber, BigDecimal amount) {
        Account accountFrom = accountRepository.findByAccountNumber(accountFromNumber).get();
        Account accountTo = accountRepository.findByAccountNumber(accountToNumber).get();

        if (accountFrom.getBalance().compareTo(amount) >= 0) {
            amount = accountFrom.getCurrency() == accountTo.getCurrency() ? amount
                    : convert(amount, accountFrom.getCurrency(), accountTo.getCurrency());
            accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
        } else {
            throw new RuntimeException("There is not enough money on the account: " + accountFrom
                    + " to transfer: " + amount + accountFrom.getCurrency());
        }

        accountTo.setBalance(accountTo.getBalance().add(amount));

        Transaction transaction = new Transaction();
        transaction.setAccountFrom(accountFrom);
        transaction.setAccountTo(accountTo);
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());
        transaction.setType(Transaction.Type.OUTCOMING);

        transactionRepository.save(transaction);
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
    }

    private BigDecimal convert(BigDecimal amount, Currency from, Currency to) {
        String urlString = "https://api.exchangerate.host/convert?from="
                .concat(String.valueOf(from)).concat("&to=").concat(String.valueOf(to))
                .concat("&amount=").concat(String.valueOf(amount));

        try {
            URL url = new URL(urlString);
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
