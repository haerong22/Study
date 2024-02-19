package org.example.delivery.api.domain.userordermenu.service;

import lombok.RequiredArgsConstructor;
import org.example.delivery.api.common.error.CommonErrorCode;
import org.example.delivery.api.common.exception.ApiException;
import org.example.delivery.db.userordermenu.UserOrderMenuEntity;
import org.example.delivery.db.userordermenu.UserOrderMenuRepository;
import org.example.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderMenuService {

    private final UserOrderMenuRepository userOrderMenuRepository;

    public List<UserOrderMenuEntity> getUserOrderMenu(Long userOrderId) {
        return userOrderMenuRepository.findAllByUserOrderIdAndStatus(userOrderId, UserOrderMenuStatus.REGISTERED);
    }

    public UserOrderMenuEntity order(UserOrderMenuEntity userOrderMenuEntity) {
        return Optional.ofNullable(userOrderMenuEntity)
                .map(it -> userOrderMenuRepository.save(it.order()))
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT));
    }
}
