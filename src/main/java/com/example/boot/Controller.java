package com.example.boot;

import com.example.boot.model.Account;
import com.example.boot.model.Currency;
import com.example.boot.model.Role;
import com.example.boot.model.User;
import com.example.boot.service.AccountService;
import com.example.boot.service.RoleService;
import com.example.boot.service.TransactionService;
import com.example.boot.service.UserService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class Controller {
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final UserService userService;
    private final RoleService roleService;

    @PostMapping("/inject")
    public void inject() {
        Role admin = new Role();
        admin.setRoleName(Role.RoleName.ADMIN);
        roleService.save(admin);

        User alex = User.builder()
                .dateOfBirth(LocalDate.now())
                .name("Alex")
                .phoneNumber("123")
                .password("12345")
                .roles(Set.of(admin))
                .build();
        userService.save(alex);

        User bob = User.builder()
                .dateOfBirth(LocalDate.now())
                .name("Bob")
                .phoneNumber("1234")
                .password("12345")
                .roles(Set.of(admin))
                .build();
        userService.save(bob);

        Account first = Account.builder()
                .accountNumber("1")
                .balance(new BigDecimal(100))
                .currency(Currency.UAH)
                .isActive(true)
                .user(alex)
                .build();
        accountService.save(first);

        Account second = Account.builder()
                .accountNumber("2")
                .balance(new BigDecimal(100))
                .currency(Currency.EUR)
                .isActive(true)
                .user(alex)
                .build();
        accountService.save(second);
    }

    @GetMapping
    public List<Account> getAll() {
        return accountService.findAllByUserPhoneNumber("123");
    }

    @PostMapping("/transfer")
    public void transfer(@RequestParam String from, @RequestParam String to,
                         @RequestParam String amount) {
        Account accountFrom = accountService.findByAccountNumber(from);
        Account accountTo = accountService.findByAccountNumber(to);
        transactionService.transfer(accountFrom, accountTo, new BigDecimal(amount));
    }
}
