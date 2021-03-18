package com.example.boot;

import com.example.boot.model.Account;
import com.example.boot.model.Currency;
import com.example.boot.model.Role;
import com.example.boot.model.User;
import com.example.boot.service.AccountService;
import com.example.boot.service.RoleService;
import com.example.boot.service.UserService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class Controller {
    private final AccountService accountService;
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
}
