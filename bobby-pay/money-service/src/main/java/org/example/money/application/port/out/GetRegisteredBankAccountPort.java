package org.example.money.application.port.out;

public interface GetRegisteredBankAccountPort {

    RegisteredBankAccountAggregateIdentifier getRegisteredBankAccount(String membershipId);
}
