package org.example.payment.application.port.out;

public interface PaymentSettlementPort {

    void changePaymentStatus(Long paymentId, int status);
}
