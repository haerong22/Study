package org.example.money.adapter.out.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.CommonHttpClient;
import org.example.money.application.port.out.GetRegisteredBankAccountPort;
import org.example.money.application.port.out.RegisteredBankAccountAggregateIdentifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BankingServiceAdapter implements GetRegisteredBankAccountPort {
    private final CommonHttpClient commonHttpClient;

    private final String bankingServiceUrl;
    private final ObjectMapper mapper;

    public BankingServiceAdapter(
            CommonHttpClient commonHttpClient,
            @Value("${service.banking.url}") String bankingServiceUrl,
            ObjectMapper mapper) {
        this.commonHttpClient = commonHttpClient;
        this.bankingServiceUrl = bankingServiceUrl;
        this.mapper = mapper;
    }

    @Override
    public RegisteredBankAccountAggregateIdentifier getRegisteredBankAccount(String membershipId) {
        final String url = String.join("/", bankingServiceUrl, "banking/account", membershipId);

        try {
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();

            RegisteredBankAccount registeredBankAccount = mapper.readValue(jsonResponse, RegisteredBankAccount.class);

            return new RegisteredBankAccountAggregateIdentifier(
                    registeredBankAccount.getRegisteredBankAccountId(),
                    registeredBankAccount.getAggregateIdentifier(),
                    registeredBankAccount.getMembershipId(),
                    registeredBankAccount.getBankName(),
                    registeredBankAccount.getBankAccountNumber()
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
