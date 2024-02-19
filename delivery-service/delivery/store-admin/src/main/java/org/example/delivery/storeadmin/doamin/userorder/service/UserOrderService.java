package org.example.delivery.storeadmin.doamin.userorder.service;

import lombok.RequiredArgsConstructor;
import org.example.delivery.db.userorder.UserOrderEntity;
import org.example.delivery.db.userorder.UserOrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    public Optional<UserOrderEntity> getUserOrder(Long userOrderId) {
        return userOrderRepository.findById(userOrderId);
    }
}
