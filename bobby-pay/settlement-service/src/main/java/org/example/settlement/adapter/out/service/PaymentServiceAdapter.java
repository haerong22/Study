package org.example.settlement.adapter.out.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.CommonHttpClient;
import org.example.settlement.port.out.PaymentPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentServiceAdapter implements PaymentPort {

    private final CommonHttpClient commonHttpClient;
    private final ObjectMapper mapper;
    private final String paymentServiceUrl;

    public PaymentServiceAdapter(CommonHttpClient commonHttpClient,
                                 @Value("${service.payment.url}") String paymentServiceUrl,
                                 ObjectMapper objectMapper) {
        this.commonHttpClient = commonHttpClient;
        this.paymentServiceUrl = paymentServiceUrl;
        this.mapper = objectMapper;
    }


    @Override
    public List<Payment> getPaymentComplete() {
        String url = String.join("/", paymentServiceUrl, "payment/complete");

        try {
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();

            return mapper.readValue(jsonResponse, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void settlement(Long paymentId) {
        String url = String.join("/", paymentServiceUrl, "payment/settlement");
        ObjectMapper mapper = new ObjectMapper();

        try {
            PaymentSettlementRequest request = new PaymentSettlementRequest(String.valueOf(paymentId));
            commonHttpClient.sendPostRequest(url, mapper.writeValueAsString(request)).body();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}