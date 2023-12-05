package org.example.payment.application.port.out;

import org.example.payment.domain.Payment;

import java.util.List;

public interface GetPaymentCompletePort {

    List<Payment> getPaymentComplete();
}
