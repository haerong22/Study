package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public Order createOrder(Order order) throws Exception {
        if (orderValidation(order)) {
            return order;
        } else {
            throw new Exception("order validation fail");
        }
    }

    private boolean orderValidation(Order order) {
        if (order.getOrderId() == null) {
            return false;
        }
        if (order.getState() == null || order.getState().length() == 0) {
            return false;
        }
        return true;
    }
}
