package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.OrderDetail;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Override
    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        return orderDetail;
    }
}
