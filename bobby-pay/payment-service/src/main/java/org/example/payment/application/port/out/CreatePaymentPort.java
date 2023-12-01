package org.example.payment.application.port.out;

import org.example.payment.domain.Payment;

public interface CreatePaymentPort {

    Payment createPayment(
            String requestMembershipId,
            int requestPrice,
            String franchiseId,
            String franchiseFeeRate
    );
}
