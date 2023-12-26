package com.example.order.domain.order.payment.validator;

import com.example.order.domain.order.Order;
import com.example.order.domain.order.OrderCommand;

public interface PaymentValidator {
    void validate(Order order, OrderCommand.PaymentRequest paymentRequest);
}