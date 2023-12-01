package org.example.payment.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPaymentCommand {

    private String requestMembershipId;

    private int requestPrice;

    private String franchiseId;

    private String franchiseFeeRate;
}
