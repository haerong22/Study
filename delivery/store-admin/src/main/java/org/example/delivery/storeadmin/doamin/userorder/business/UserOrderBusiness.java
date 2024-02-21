package org.example.delivery.storeadmin.doamin.userorder.business;

import lombok.RequiredArgsConstructor;
import org.example.delivery.common.message.model.UserOrderMessage;
import org.example.delivery.db.userorder.UserOrderEntity;
import org.example.delivery.db.userordermenu.UserOrderMenuEntity;
import org.example.delivery.storeadmin.common.annotation.Business;
import org.example.delivery.storeadmin.doamin.sse.connection.SseConnectionPool;
import org.example.delivery.storeadmin.doamin.sse.connection.model.UserSseConnection;
import org.example.delivery.storeadmin.doamin.storemenu.controller.model.StoreMenuResponse;
import org.example.delivery.storeadmin.doamin.storemenu.converter.StoreMenuConverter;
import org.example.delivery.storeadmin.doamin.storemenu.service.StoreMenuService;
import org.example.delivery.storeadmin.doamin.userorder.controller.model.UserOrderDetailResponse;
import org.example.delivery.storeadmin.doamin.userorder.controller.model.UserOrderResponse;
import org.example.delivery.storeadmin.doamin.userorder.converter.UserOrderConverter;
import org.example.delivery.storeadmin.doamin.userorder.service.UserOrderService;
import org.example.delivery.storeadmin.doamin.userordermenu.service.UserOrderMenuService;

import java.util.List;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final SseConnectionPool sseConnectionPool;

    private final UserOrderService userOrderService;
    private final UserOrderConverter userOrderConverter;

    private final UserOrderMenuService userOrderMenuService;

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    public void pushUserOrder(UserOrderMessage userOrderMessage) {
        UserOrderEntity userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId())
                .orElseThrow(() -> new RuntimeException("사용자 주문내역 없음"));

        List<UserOrderMenuEntity> userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());

        List<StoreMenuResponse> storeMenuResponseList = userOrderMenuList.stream()
                .map(it -> storeMenuService.getStoreMenuWithThrow(it.getStoreMenu().getId()))
                .map(storeMenuConverter::toResponse)
                .toList();

        UserOrderResponse userOrderResponse = userOrderConverter.toResponse(userOrderEntity);

        UserOrderDetailResponse message = UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderResponse)
                .storeMenuResponseList(storeMenuResponseList)
                .build();

        UserSseConnection connection = sseConnectionPool.getSession(userOrderEntity.getStore().getId().toString());

        connection.sendMessage(message);
    }
}
