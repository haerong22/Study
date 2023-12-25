package com.example.order.infrastructure.order.payment;

import com.example.order.domain.order.OrderCommand;
import com.example.order.domain.order.payment.PayMethod;

public interface PaymentApiCaller {
    boolean support(PayMethod payMethod);
    void pay(OrderCommand.PaymentRequest request);
}