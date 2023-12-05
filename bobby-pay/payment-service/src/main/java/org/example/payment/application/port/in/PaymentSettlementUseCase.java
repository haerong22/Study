package org.example.payment.application.port.in;

public interface PaymentSettlementUseCase {

    void paymentSettlement(PaymentSettlementCommand command);
}
