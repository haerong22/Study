package org.example.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.delivery.common.annotation.Business;
import org.example.delivery.api.domain.store.converter.StoreConverter;
import org.example.delivery.api.domain.store.service.StoreService;
import org.example.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.example.delivery.api.domain.storemenu.service.StoreMenuService;
import org.example.delivery.api.domain.user.model.User;
import org.example.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.example.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.example.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.example.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.example.delivery.api.domain.userorder.producer.UserOrderProducer;
import org.example.delivery.api.domain.userorder.service.UserOrderService;
import org.example.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.example.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import org.example.delivery.db.store.StoreEntity;
import org.example.delivery.db.storemenu.StoreMenuEntity;
import org.example.delivery.db.userorder.UserOrderEntity;
import org.example.delivery.db.userordermenu.UserOrderMenuEntity;
import org.example.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Business
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreService storeService;

    private final UserOrderConverter userOrderConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final StoreMenuConverter storeMenuConverter;
    private final StoreConverter storeConverter;
    private final UserOrderProducer userOrderProducer;

    @Transactional
    public UserOrderResponse userOrder(User user, UserOrderRequest userOrderRequest) {
        StoreEntity storeEntity = storeService.getStoreWithThrow(userOrderRequest.getStoreId());

        List<StoreMenuEntity> storeMenuEntityList = userOrderRequest.getStoreMenuIdList().stream()
                .map(storeMenuService::getStoreMenuWithThrow)
                .toList();

        UserOrderEntity userOrderEntity = userOrderConverter.toEntity(user, storeEntity, storeMenuEntityList);

        UserOrderEntity ordered = userOrderService.order(userOrderEntity);

        List<UserOrderMenuEntity> userOrderMenuEntityList = storeMenuEntityList.stream()
                .map(it -> userOrderMenuConverter.toEntity(ordered, it))
                .toList();

        userOrderMenuEntityList.forEach(userOrderMenuService::order);

        userOrderProducer.sendOrder(ordered);

        return userOrderConverter.toResponse(ordered);
    }

    public List<UserOrderDetailResponse> current(User user) {

        List<UserOrderEntity> userOrderEntityList = userOrderService.current(user.getId());

        return userOrderEntityList.stream()
                .map(userOrder -> {
                    log.info("사용자의 주문 정보 : {}", userOrder);

                    List<StoreMenuEntity> storeMenuEntityList = userOrder.getUserOrderMenuList().stream()
                            .filter(userOrderMenu -> userOrderMenu.getStatus().equals(UserOrderMenuStatus.REGISTERED))
                            .toList().stream()
                            .map(UserOrderMenuEntity::getStoreMenu)
                            .toList();

                    StoreEntity storeEntity = userOrder.getStore();

                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(userOrder))
                            .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                            .storeResponse(storeConverter.toResponse(storeEntity))
                            .build();
                })
                .toList();
    }

    public List<UserOrderDetailResponse> history(User user) {
        List<UserOrderEntity> userOrderEntityList = userOrderService.history(user.getId());

        return userOrderEntityList.stream()
                .map(userOrder -> {

                    List<StoreMenuEntity> storeMenuEntityList = userOrder.getUserOrderMenuList().stream()
                            .filter(userOrderMenu -> userOrderMenu.getStatus().equals(UserOrderMenuStatus.REGISTERED))
                            .toList().stream()
                            .map(UserOrderMenuEntity::getStoreMenu)
                            .toList();

                    StoreEntity storeEntity = userOrder.getStore();

                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(userOrder))
                            .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                            .storeResponse(storeConverter.toResponse(storeEntity))
                            .build();
                })
                .toList();
    }

    public UserOrderDetailResponse read(User user, Long orderId) {

        UserOrderEntity userOrderEntity = userOrderService.getUserOrderWithOutStatusWithThrow(orderId, user.getId());

        List<StoreMenuEntity> storeMenuEntityList = userOrderEntity.getUserOrderMenuList().stream()
                .filter(userOrderMenu -> userOrderMenu.getStatus().equals(UserOrderMenuStatus.REGISTERED))
                .toList().stream()
                .map(UserOrderMenuEntity::getStoreMenu)
                .toList();

        StoreEntity storeEntity = userOrderEntity.getStore();

        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build();
    }
}
