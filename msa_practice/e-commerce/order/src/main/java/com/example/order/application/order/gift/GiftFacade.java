package com.example.order.application.order.gift;

import com.example.order.domain.order.OrderCommand;
import com.example.order.domain.order.gift.GiftOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiftFacade {
    private final GiftOrderService giftOrderService;

    public void paymentOrder(OrderCommand.PaymentRequest request) {
        giftOrderService.paymentOrder(request);
    }
}