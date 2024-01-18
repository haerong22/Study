package com.example.order.domain.order.gift;

public interface GiftMessageChannelSender {
    void paymentComplete(GiftPaymentCompleteMessage message);
}
