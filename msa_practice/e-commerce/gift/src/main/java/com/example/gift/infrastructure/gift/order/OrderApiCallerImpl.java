package com.example.gift.infrastructure.gift.order;

import com.example.gift.common.response.CommonResponse;
import com.example.gift.domain.gift.GiftCommand;
import com.example.gift.domain.gift.order.OrderApiCaller;
import com.example.gift.domain.gift.order.OrderApiCommand;
import com.example.gift.infrastructure.gift.retrofit.RetrofitUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderApiCallerImpl implements OrderApiCaller {
    private final RetrofitUtils retrofitUtils;
    private final RetrofitOrderApi retrofitOrderApi;

    @Override
    public String registerGiftOrder(OrderApiCommand.Register request) {
        var call = retrofitOrderApi.registerOrder(request);
        return retrofitUtils.responseSync(call)
                .map(CommonResponse::getData)
                .map(RetrofitOrderApiResponse.Register::getOrderToken)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void updateReceiverInfo(String orderToken, GiftCommand.Accept request) {
        var call = retrofitOrderApi.updateReceiverInfo(orderToken, request);
        retrofitUtils.responseVoid(call);
    }
}