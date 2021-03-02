package com.example.boot.repository;

import com.example.boot.model.Account;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserPhoneNumber(String phoneNumber);

    Optional<Account> findByAccountNumber(String accountNumber);
}
