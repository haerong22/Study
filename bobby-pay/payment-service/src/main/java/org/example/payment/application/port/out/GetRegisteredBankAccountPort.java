package org.example.payment.application.port.out;

public interface GetRegisteredBankAccountPort {

    RegisteredBankAccountAggregateIdentifier getRegisteredBankAccount(String membershipId);
}
