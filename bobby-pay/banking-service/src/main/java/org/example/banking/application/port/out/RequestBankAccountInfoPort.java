package org.example.banking.application.port.out;

import org.example.banking.adapter.out.external.bank.BankAccount;
import org.example.banking.adapter.out.external.bank.GetBankAccountRequest;

public interface RequestBankAccountInfoPort {

    BankAccount getBankAccountInfo(GetBankAccountRequest request);
}
