package org.example.banking.application.service;

import lombok.RequiredArgsConstructor;
import org.example.banking.adapter.out.external.bank.BankAccount;
import org.example.banking.adapter.out.external.bank.GetBankAccountRequest;
import org.example.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import org.example.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import org.example.banking.application.port.in.RegisterBankAccountCommand;
import org.example.banking.application.port.in.RegisterBankAccountUseCase;
import org.example.banking.application.port.out.RegisterBankAccountPort;
import org.example.banking.application.port.out.RequestBankAccountInfoPort;
import org.example.banking.domain.RegisteredBankAccount;
import org.example.common.UseCase;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterBankAccountService implements RegisterBankAccountUseCase {

    private final RegisterBankAccountPort registerBankAccountPort;
    private final RegisteredBankAccountMapper mapper;
    private final RequestBankAccountInfoPort requestBankAccountInfoPort;

    @Override
    public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {

        BankAccount accountInfo = requestBankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(command.getBankName(), command.getBankAccountNumber()));

        boolean accountIsValid = accountInfo.isValid();

        if (accountIsValid) {

            RegisteredBankAccountJpaEntity savedAccountInfo = registerBankAccountPort.createRegisteredBankAccount(
                    new RegisteredBankAccount.MembershipId(command.getMembershipId() + ""),
                    new RegisteredBankAccount.BankName(accountInfo.getBankName()),
                    new RegisteredBankAccount.BankAccountNumber(accountInfo.getBankAccountNumber()),
                    new RegisteredBankAccount.LinkedStatusIsValid(true)
            );

            return mapper.mapToDomainEntity(savedAccountInfo);
        } else {
            return null;
        }

    }
}
