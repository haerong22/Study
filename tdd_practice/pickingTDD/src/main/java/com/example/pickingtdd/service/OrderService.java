package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.Order;
import com.example.pickingtdd.entity.OrderStateEnum;

public interface OrderService {

    Order createOrder(Order order) throws Exception;
    boolean changeOrderState(Order order, OrderStateEnum state);
}
