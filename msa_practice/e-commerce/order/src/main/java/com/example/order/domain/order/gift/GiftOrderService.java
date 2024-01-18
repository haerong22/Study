package com.example.order.domain.order.gift;

import com.example.order.domain.order.OrderCommand;

public interface GiftOrderService {
    void paymentOrder(OrderCommand.PaymentRequest paymentRequest);
}