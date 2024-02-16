package org.example.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.example.delivery.api.common.annotation.Business;
import org.example.delivery.api.domain.storemenu.service.StoreMenuService;
import org.example.delivery.api.domain.user.model.User;
import org.example.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.example.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.example.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.example.delivery.api.domain.userorder.service.UserOrderService;
import org.example.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.example.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import org.example.delivery.db.storemenu.StoreMenuEntity;
import org.example.delivery.db.userorder.UserOrderEntity;
import org.example.delivery.db.userordermenu.UserOrderMenuEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;
    private final UserOrderMenuService userOrderMenuService;
    private final UserOrderConverter userOrderConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;

    @Transactional
    public UserOrderResponse userOrder(User user, UserOrderRequest userOrderRequest) {
        List<StoreMenuEntity> storeMenuEntityList = userOrderRequest.getStoreMenuIdList().stream()
                .map(storeMenuService::getStoreMenuWithThrow)
                .toList();

        UserOrderEntity userOrderEntity = userOrderConverter.toEntity(user, storeMenuEntityList);

        UserOrderEntity ordered = userOrderService.order(userOrderEntity);

        List<UserOrderMenuEntity> userOrderMenuEntityList = storeMenuEntityList.stream()
                .map(it -> userOrderMenuConverter.toEntity(ordered, it))
                .toList();

        userOrderMenuEntityList.forEach(userOrderMenuService::order);

        return userOrderConverter.toResponse(ordered);
    }
}
