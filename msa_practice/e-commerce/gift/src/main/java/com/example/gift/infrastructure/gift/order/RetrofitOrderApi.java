package com.example.gift.infrastructure.gift.order;

import com.example.gift.common.response.CommonResponse;
import com.example.gift.domain.gift.GiftCommand;
import com.example.gift.domain.gift.order.OrderApiCommand;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitOrderApi {

    @POST("api/v1/gift-orders/init")
    Call<CommonResponse<RetrofitOrderApiResponse.Register>> registerOrder(@Body OrderApiCommand.Register request);

    @POST("api/v1/gift-orders/{orderToken}/update-receiver-info")
    Call<Void> updateReceiverInfo(@Path("orderToken") String orderToken, @Body GiftCommand.Accept request);
}