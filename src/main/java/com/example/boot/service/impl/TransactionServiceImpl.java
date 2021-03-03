package com.example.boot.service.impl;

import com.example.boot.model.Account;
import com.example.boot.model.Transaction;
import com.example.boot.repository.AccountRepository;
import com.example.boot.repository.TransactionRepository;
import com.example.boot.service.Converter;
import com.example.boot.service.TransactionService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final Converter converter;

    @Override
    public List<Transaction> getAllByAccount(int page, int size, Account account) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return transactionRepository.findAllByAccount(account, pageable);
    }

    @Override
    @Transactional
    public void transfer(Account accountFrom, Account accountTo, BigDecimal amount) {
        if (accountFrom.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException(String.format("There is not enough money on the account: %s"
                    + "to transfer: %s %s", accountFrom, amount, accountFrom.getCurrency()));
        }

        accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
        amount = accountFrom.getCurrency() == accountTo.getCurrency() ? amount :
                converter.convert(accountFrom.getCurrency(), accountTo.getCurrency(), amount);
        accountTo.setBalance(accountTo.getBalance().add(amount));
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);

        LocalDateTime dateTime = LocalDateTime.now();
        Transaction inComingTransaction = Transaction.builder()
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .amount(amount)
                .date(dateTime)
                .type(Transaction.Type.INCOMING)
                .build();
        transactionRepository.save(inComingTransaction);

        Transaction outComingTransaction = Transaction.builder()
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .amount(amount)
                .date(dateTime)
                .type(Transaction.Type.OUTCOMING)
                .build();
        transactionRepository.save(outComingTransaction);
    }
}
