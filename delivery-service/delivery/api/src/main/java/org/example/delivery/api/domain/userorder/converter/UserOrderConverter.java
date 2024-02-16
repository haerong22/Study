package org.example.delivery.api.domain.userorder.converter;

import org.example.delivery.api.common.annotation.Converter;
import org.example.delivery.api.domain.user.model.User;
import org.example.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.example.delivery.db.storemenu.StoreMenuEntity;
import org.example.delivery.db.userorder.UserOrderEntity;

import java.math.BigDecimal;
import java.util.List;

@Converter
public class UserOrderConverter {

    public UserOrderEntity toEntity(User user, List<StoreMenuEntity> storeMenuEntityList) {
        BigDecimal totalAmount = storeMenuEntityList.stream()
                .map(StoreMenuEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return UserOrderEntity.builder()
                .userId(user.getId())
                .amount(totalAmount)
                .build();
    }

    public UserOrderResponse toResponse(UserOrderEntity entity) {

        return UserOrderResponse.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .amount(entity.getAmount())
                .orderedAt(entity.getOrderedAt())
                .acceptedAt(entity.getAcceptedAt())
                .cookingStartAt(entity.getCookingStartAt())
                .deliveryStartedAt(entity.getDeliveryStartedAt())
                .receivedAt(entity.getReceivedAt())
                .build();
    }
}
