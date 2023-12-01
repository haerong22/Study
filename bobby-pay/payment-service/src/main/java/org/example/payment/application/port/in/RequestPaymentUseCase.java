package org.example.payment.application.port.in;

import org.example.payment.domain.Payment;

public interface RequestPaymentUseCase {

    Payment requestPayment(RequestPaymentCommand command);
}
