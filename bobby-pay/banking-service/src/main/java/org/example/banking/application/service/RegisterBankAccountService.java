package org.example.banking.application.service;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.banking.adapter.axon.command.CreateRegisteredBankAccountCommand;
import org.example.banking.adapter.out.external.bank.BankAccount;
import org.example.banking.adapter.out.external.bank.GetBankAccountRequest;
import org.example.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import org.example.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import org.example.banking.adapter.out.service.MembershipStatus;
import org.example.banking.application.port.in.GetRegisteredBankAccountCommand;
import org.example.banking.application.port.in.GetRegisteredBankAccountUseCase;
import org.example.banking.application.port.in.RegisterBankAccountCommand;
import org.example.banking.application.port.in.RegisterBankAccountUseCase;
import org.example.banking.application.port.out.GetMembershipPort;
import org.example.banking.application.port.out.GetRegisteredBankAccountPort;
import org.example.banking.application.port.out.RegisterBankAccountPort;
import org.example.banking.application.port.out.RequestBankAccountInfoPort;
import org.example.banking.domain.RegisteredBankAccount;
import org.example.common.UseCase;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterBankAccountService implements RegisterBankAccountUseCase, GetRegisteredBankAccountUseCase {

    private final RegisterBankAccountPort registerBankAccountPort;
    private final GetMembershipPort getMembershipPort;
    private final RegisteredBankAccountMapper mapper;
    private final RequestBankAccountInfoPort requestBankAccountInfoPort;
    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;
    private final CommandGateway commandGateway;

    @Override
    public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {

        MembershipStatus membershipStatus = getMembershipPort.getMembership(command.getMembershipId());

        if (!membershipStatus.isValid()) {
            return null;
        }

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

    @Override
    public void registerBankAccountByEvent(RegisterBankAccountCommand command) {
        commandGateway.send(new CreateRegisteredBankAccountCommand(command.getMembershipId(), command.getBankName(), command.getBankAccountNumber()))
                .whenComplete(
                        (result, throwable) -> {
                            if (throwable != null) {
                                throwable.printStackTrace();
                                throw new RuntimeException(throwable);
                            } else {
                                // 정상적으로 이벤트 소싱
                                registerBankAccountPort.createRegisteredBankAccount(
                                        new RegisteredBankAccount.MembershipId(command.getMembershipId() + ""),
                                        new RegisteredBankAccount.BankName(command.getBankName()),
                                        new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                                        new RegisteredBankAccount.LinkedStatusIsValid(true)
                                );
                            }
                        }
                );
    }

    @Override
    public RegisteredBankAccount getRegisteredBankAccount(GetRegisteredBankAccountCommand command) {
        return mapper.mapToDomainEntity(getRegisteredBankAccountPort.getRegisteredBankAccount(command));
    }
}
