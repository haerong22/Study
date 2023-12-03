package org.example.money.aggregation.adapter.out.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.common.CommonHttpClient;
import org.example.common.ExternalSystemAdapter;
import org.example.money.aggregation.application.port.out.GetMoneySumPort;
import org.example.money.aggregation.application.port.out.MemberMoney;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class MoneyServiceAdapter implements GetMoneySumPort {

    private final CommonHttpClient commonHttpClient;
    private final ObjectMapper mapper;

    @Value("${service.money.url}")
    private String moneyServiceEndpoint;

    @Override
    public List<MemberMoney> getMoneySumByMembershipIds(List<Long> membershipIds) {

        final String url = String.join("/", moneyServiceEndpoint, "money/member-money");

        try {
            FindMemberMoneyRequest request = new FindMemberMoneyRequest(membershipIds);
            String jsonResponse = commonHttpClient.sendPostRequest(url, mapper.writeValueAsString(request)).body();

            return mapper.readValue(jsonResponse, new TypeReference<>() {});

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}