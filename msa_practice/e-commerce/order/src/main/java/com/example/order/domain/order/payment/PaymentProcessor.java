package com.example.order.domain.order.payment;

import com.example.order.domain.order.OrderCommand;

public interface PaymentProcessor {
    void pay(OrderCommand.PaymentRequest request);
}