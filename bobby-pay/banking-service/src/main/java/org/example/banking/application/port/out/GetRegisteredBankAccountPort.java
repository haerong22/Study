package org.example.banking.application.port.out;

import org.example.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import org.example.banking.application.port.in.GetRegisteredBankAccountCommand;

public interface GetRegisteredBankAccountPort {

    RegisteredBankAccountJpaEntity getRegisteredBankAccount(GetRegisteredBankAccountCommand command);
}
