package org.example.banking.adapter.out.persistence;

import org.example.banking.domain.RegisteredBankAccount;
import org.springframework.stereotype.Component;

@Component
public class RegisteredBankAccountMapper {

    public RegisteredBankAccount mapToDomainEntity(RegisteredBankAccountJpaEntity membershipJpaEntity) {
        return RegisteredBankAccount.generateRegisteredBankAccount(
                new RegisteredBankAccount.RegisteredBankAccountId(membershipJpaEntity.getRegisteredBankAccountId()+""),
                new RegisteredBankAccount.MembershipId(membershipJpaEntity.getMembershipId()),
                new RegisteredBankAccount.BankName(membershipJpaEntity.getBankName()),
                new RegisteredBankAccount.BankAccountNumber(membershipJpaEntity.getBankAccountNumber()),
                new RegisteredBankAccount.LinkedStatusIsValid(membershipJpaEntity.isLinkedStatusIsValid())
        );
    }
}
