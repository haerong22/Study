package org.example.banking.adapter.out.external.bank;

import lombok.RequiredArgsConstructor;
import org.example.banking.application.port.out.RequestBankAccountInfoPort;
import org.example.common.ExternalSystemAdapter;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankAccountAdapter implements RequestBankAccountInfoPort {

    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request) {
        return new BankAccount(request.getBankName(), request.getBankAccountNumber(), true);
    }
}
