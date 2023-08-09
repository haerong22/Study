package org.example.remittance.adapter.out.service.banking;

import lombok.RequiredArgsConstructor;
import org.example.common.CommonHttpClient;
import org.example.common.ExternalSystemAdapter;
import org.example.remittance.application.port.out.banking.BankingInfo;
import org.example.remittance.application.port.out.banking.BankingPort;
import org.springframework.beans.factory.annotation.Value;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankingServiceAdapter implements BankingPort {

    private final CommonHttpClient bankingServiceHttpClient;

    @Value("${service.banking.url}")
    private String bankingServiceEndpoint;


    @Override
    public BankingInfo getMembershipBankingInfo(String bankName, String bankAccountNumber) {

        return null;
    }

    @Override
    public boolean requestFirmbanking(String bankName, String bankAccountNumber, int amount) {
        return false;
    }
}