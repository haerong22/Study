package org.example.settlement.adapter.out.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.CommonHttpClient;
import org.example.settlement.port.out.GetRegisteredBankAccountPort;
import org.example.settlement.port.out.RegisteredBankAccountAggregateIdentifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BankingServiceAdapter implements GetRegisteredBankAccountPort {

    private final CommonHttpClient commonHttpClient;

    private final String bankingServiceUrl;
    private final ObjectMapper mapper;

    public BankingServiceAdapter(CommonHttpClient commonHttpClient,
                                 @Value("${service.banking.url}") String membershipServiceUrl,
                                 ObjectMapper objectMapper) {
        this.commonHttpClient = commonHttpClient;
        this.bankingServiceUrl = membershipServiceUrl;
        this.mapper = objectMapper;
    }

    @Override
    public RegisteredBankAccountAggregateIdentifier getRegisteredBankAccount(String membershipId){
        String url = String.join("/", bankingServiceUrl, "banking/account", membershipId);
        try {
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();
            // json RegisteredBankAccount

            RegisteredBankAccount registeredBankAccount = mapper.readValue(jsonResponse, RegisteredBankAccount.class);

            return new RegisteredBankAccountAggregateIdentifier(
                    registeredBankAccount.getRegisteredBankAccountId(),
                    registeredBankAccount.getAggregateIdentifier(),
                    registeredBankAccount.getBankName(),
                    registeredBankAccount.getBankName(),
                    registeredBankAccount.getBankAccountNumber()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void requestFirmBanking(String bankName, String bankAccountNumber, int moneyAmount) {
        String url = String.join("/", bankingServiceUrl, "banking/firmbanking/request");
        try {
            ObjectMapper mapper = new ObjectMapper();
            RequestFirmBankingRequest request = new RequestFirmBankingRequest(
                    "bobbyBank",
                    "111-222-333",
                    bankName,
                    bankAccountNumber,
                    moneyAmount);

            commonHttpClient.sendPostRequest(url, mapper.writeValueAsString(request)).body();
            // FirmbankingRequest response = mapper.readValue(jsonResponse, new TypeReference<>() {});

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}