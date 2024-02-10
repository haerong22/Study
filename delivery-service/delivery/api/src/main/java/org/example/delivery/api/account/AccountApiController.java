package org.example.delivery.api.account;

import lombok.RequiredArgsConstructor;
import org.example.delivery.db.account.AccountEntity;
import org.example.delivery.db.account.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountApiController {

    private final AccountRepository accountRepository;

    @GetMapping
    public void save() {
        accountRepository.save(AccountEntity.builder().build());
    }
}
