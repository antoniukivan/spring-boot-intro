package com.example.boot.service.impl;

import com.example.boot.model.Account;
import com.example.boot.model.Transaction;
import com.example.boot.repository.AccountRepository;
import com.example.boot.repository.TransactionRepository;
import com.example.boot.service.TransactionService;
import java.math.BigDecimal;
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
    public void transfer(Account accountFrom, Account accountTo, BigDecimal amount) {
        if (accountFrom.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("There is not enough money on the account: "
                    + accountFrom + " to transfer: " + amount + accountFrom.getCurrency());
        }

        Transaction inComingTransaction = Transaction.builder()
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .amount(amount)
                .date(LocalDateTime.now())
                .type(Transaction.Type.INCOMING)
                .build();

        Transaction outComingTransaction = Transaction.builder()
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .amount(amount)
                .date(LocalDateTime.now())
                .type(Transaction.Type.OUTCOMING)
                .build();

        transactionRepository.save(inComingTransaction);
        transactionRepository.save(outComingTransaction);
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
    }
}
