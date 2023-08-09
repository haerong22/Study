package org.example.remittance.adapter.out.service.money;

import lombok.RequiredArgsConstructor;
import org.example.common.CommonHttpClient;
import org.example.common.ExternalSystemAdapter;
import org.example.remittance.application.port.out.money.MoneyInfo;
import org.example.remittance.application.port.out.money.MoneyPort;
import org.springframework.beans.factory.annotation.Value;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class MoneyServiceAdapter implements MoneyPort {

    private final CommonHttpClient moneyServiceHttpClient;

    @Value("${service.money.url}")
    private String moneyServiceEndpoint;


    @Override
    public MoneyInfo getMoneyInfo(String membershipId) {
        return null;
    }

    @Override
    public boolean requestMoneyRecharging(String membershipId, int amount) {
        return false;
    }

    @Override
    public boolean requestMoneyIncrease(String membershipId, int amount) {
        return false;
    }

    @Override
    public boolean requestMoneyDecrease(String membershipId, int amount) {
        return false;
    }
}