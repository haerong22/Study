package org.example.banking.adapter.in.web;


import lombok.RequiredArgsConstructor;
import org.example.banking.application.port.in.RegisterBankAccountCommand;
import org.example.banking.application.port.in.RegisterBankAccountUseCase;
import org.example.banking.domain.RegisteredBankAccount;
import org.example.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterBankAccountController {

    private final RegisterBankAccountUseCase registerBankAccountUseCase;

    @PostMapping("/banking/account/register")
    RegisteredBankAccount registeredBankAccount(@RequestBody RegisterBankAccountRequest request) {

        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
                .membershipId(request.getMembershipId())
                .bankName(request.getBankName())
                .bankAccountNumber(request.getBankAccountNumber())
                .isValid(true)
                .build();

        RegisteredBankAccount registeredBankAccount = registerBankAccountUseCase.registerBankAccount(command);

        if (registeredBankAccount == null) {

            // TODO Error Handling
            return null;
        }

        return registeredBankAccount;
    }

    @PostMapping("/banking/account/register-eda")
    void registeredBankAccountByEvent(@RequestBody RegisterBankAccountRequest request) {

        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
                .membershipId(request.getMembershipId())
                .bankName(request.getBankName())
                .bankAccountNumber(request.getBankAccountNumber())
                .isValid(true)
                .build();

        registerBankAccountUseCase.registerBankAccountByEvent(command);
    }
}
