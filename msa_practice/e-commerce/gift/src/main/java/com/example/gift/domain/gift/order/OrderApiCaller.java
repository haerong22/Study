package com.example.gift.domain.gift.order;

import com.example.gift.domain.gift.GiftCommand;

public interface OrderApiCaller {
    String registerGiftOrder(OrderApiCommand.Register request);

    void updateReceiverInfo(String orderToken, GiftCommand.Accept request);
}