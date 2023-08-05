package org.example.money.adapter.out.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.CommonHttpClient;
import org.example.money.application.port.out.GetMembershipPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
    public MembershipStatus getMembership(String membershipId) {

        final String url = String.join("/", membershipServiceUrl, "membership", membershipId);

        try {
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();

            Membership membership = mapper.readValue(jsonResponse, Membership.class);

            return new MembershipStatus(membership.getMembershipId(), membership.isValid());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
