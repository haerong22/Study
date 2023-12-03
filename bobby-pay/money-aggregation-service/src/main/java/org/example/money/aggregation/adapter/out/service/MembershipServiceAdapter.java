package org.example.money.aggregation.adapter.out.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.CommonHttpClient;
import org.example.money.aggregation.application.port.out.GetMembershipPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MembershipServiceAdapter implements GetMembershipPort {

    private final CommonHttpClient commonHttpClient;

    private final String membershipServiceUrl;
    private final ObjectMapper mapper;

    public MembershipServiceAdapter(
            CommonHttpClient commonHttpClient,
            @Value("${service.membership.url}") String membershipServiceUrl,
            ObjectMapper mapper) {
        this.commonHttpClient = commonHttpClient;
        this.membershipServiceUrl = membershipServiceUrl;
        this.mapper = mapper;
    }

    @Override
    public List<Long> getMembershipByAddress(String address) {
        final String url = String.join("/", membershipServiceUrl, "membership/address", address);

        try {
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();

            List<Membership> membershipList = mapper.readValue(jsonResponse, new TypeReference<>() {});
            return membershipList.stream()
                    .mapToLong(Membership::getMembershipId)
                    .boxed()
                    .collect(Collectors.toList());


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
