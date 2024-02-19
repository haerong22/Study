package org.example.delivery.storeadmin.doamin.userordermenu.service;

import lombok.RequiredArgsConstructor;
import org.example.delivery.db.userordermenu.UserOrderMenuEntity;
import org.example.delivery.db.userordermenu.UserOrderMenuRepository;
import org.example.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserOrderMenuService {

    private final UserOrderMenuRepository userOrderMenuRepository;

    public List<UserOrderMenuEntity> getUserOrderMenuList(Long userOrderId){
        return userOrderMenuRepository.findAllByUserOrderIdAndStatus(userOrderId, UserOrderMenuStatus.REGISTERED);
    }

}