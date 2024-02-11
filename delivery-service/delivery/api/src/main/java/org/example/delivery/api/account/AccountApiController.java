package org.example.delivery.api.account;

import lombok.RequiredArgsConstructor;
import org.example.delivery.api.account.model.AccountMeResponse;
import org.example.delivery.api.common.api.Api;
import org.example.delivery.db.account.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountApiController {

    private final AccountRepository accountRepository;

    @GetMapping("/me")
    public Api<AccountMeResponse> me() {
        AccountMeResponse response = AccountMeResponse.builder()
                .name("홍길동")
                .email("test@test.com")
                .registeredAt(LocalDateTime.now())
                .build();

        return Api.ok(response);
    }
}
