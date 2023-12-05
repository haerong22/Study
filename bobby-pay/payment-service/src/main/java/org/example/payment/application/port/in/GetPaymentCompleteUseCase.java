package org.example.payment.application.port.in;

import org.example.payment.domain.Payment;

import java.util.List;

public interface GetPaymentCompleteUseCase {

    List<Payment> getPaymentComplete();
}
