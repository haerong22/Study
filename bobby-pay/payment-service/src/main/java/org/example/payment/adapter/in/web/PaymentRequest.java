package org.example.payment.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private String requestMembershipId;

    private int requestPrice;

    private String franchiseId;

    private String franchiseFeeRate;
}
