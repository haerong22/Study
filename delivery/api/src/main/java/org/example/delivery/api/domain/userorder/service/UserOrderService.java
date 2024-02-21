package org.example.delivery.api.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.example.delivery.common.error.CommonErrorCode;
import org.example.delivery.common.exception.ApiException;
import org.example.delivery.db.userorder.UserOrderEntity;
import org.example.delivery.db.userorder.UserOrderRepository;
import org.example.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.example.delivery.db.userorder.enums.UserOrderStatus.*;

@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    public UserOrderEntity getUserOrderWithOutStatusWithThrow(Long id, Long userId) {
        return Optional.ofNullable(userOrderRepository.findByIdAndUserId(id, userId))
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT));
    }

    public UserOrderEntity getUserOrderWithThrow(Long id, Long userId) {
        return Optional.ofNullable(userOrderRepository.findByIdAndStatusAndUserId(id, REGISTERED, userId))
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT));
    }

    public List<UserOrderEntity> getUserOrderList(Long userId) {
        return userOrderRepository.findAllByUserIdAndStatusOrderByIdDesc(userId, REGISTERED);
    }

    public List<UserOrderEntity> getUserOrderList(Long userId, List<UserOrderStatus> statusList) {
        return userOrderRepository.findAllByUserIdAndStatusInOrderByIdDesc(userId, statusList);
    }

    public List<UserOrderEntity> current(Long userId) {
        return getUserOrderList(
                userId,
                List.of(ORDER, COOKING, DELIVERY, ACCEPT)
        );
    }

    public List<UserOrderEntity> history(Long userId) {
        return getUserOrderList(
                userId,
                List.of(RECEIVE)
        );
    }

    public UserOrderEntity order(UserOrderEntity userOrderEntity) {
        return Optional.ofNullable(userOrderEntity)
                .map(it -> userOrderRepository.save(it.order()))
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT));
    }

    public UserOrderEntity accept(UserOrderEntity userOrderEntity) {
        return Optional.ofNullable(userOrderEntity)
                .map(it -> userOrderRepository.save(it.accept()))
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT));
    }

    public UserOrderEntity cooking(UserOrderEntity userOrderEntity) {
        return Optional.ofNullable(userOrderEntity)
                .map(it -> userOrderRepository.save(it.cooking()))
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT));
    }

    public UserOrderEntity delivery(UserOrderEntity userOrderEntity) {
        return Optional.ofNullable(userOrderEntity)
                .map(it -> userOrderRepository.save(it.delivery()))
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT));
    }

    public UserOrderEntity receive(UserOrderEntity userOrderEntity) {
        return Optional.ofNullable(userOrderEntity)
                .map(it -> userOrderRepository.save(it.receive()))
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT));
    }
}
