package com.example.boot.repository;

import com.example.boot.model.Account;
import com.example.boot.model.Transaction;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t "
            + "WHERE t.accountFrom = ?1 OR t.accountTo = ?1")
    List<Transaction> findAllByAccount(Account account, Pageable pageable);
}
