package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.Order;
import com.example.pickingtdd.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDetailService orderDetailService;

    @Override
    public Order createOrder(Order order) throws Exception {
        if (orderValidation(order)) {
            for (OrderDetail orderDetail : order.getOrderDetailList()) {
                orderDetailService.createOrderDetail(orderDetail);
            }
            return order;
        } else {
            throw new Exception("order validation fail");
        }
    }

    private boolean orderValidation(Order order) {
        if (order.getOrderId() == null) {
            return false;
        }
        if (order.getState() == null) {
            return false;
        }
        if (order.getOrderDetailList() == null || order.getOrderDetailList().size() < 1) {
            return false;
        }
        return true;
    }
}
