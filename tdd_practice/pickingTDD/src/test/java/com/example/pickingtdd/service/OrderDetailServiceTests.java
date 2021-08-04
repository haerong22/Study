package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.OrderDetail;
import com.example.pickingtdd.entity.Sku;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderDetailServiceTests {

    @Autowired
    OrderDetailService orderDetailService;

    OrderDetail orderDetailSuccess;
    OrderDetail orderDetailFail;

    @BeforeEach
    void setup() {
        orderDetailSuccess = new OrderDetail();
        orderDetailSuccess.setOrderDetailId(1L);
        orderDetailSuccess.setOrderId(1L);
        orderDetailSuccess.setSku(new Sku());
        orderDetailSuccess.setAmount(10);

        orderDetailFail = new OrderDetail();
        orderDetailFail.setOrderDetailId(2L);
    }

    @Test
    void createOrderDetail_success() {
        OrderDetail orderDetail = new OrderDetail();

        try {
            orderDetail = orderDetailService.createOrderDetail(orderDetailSuccess);
        } catch (Exception e) {
            // do Something
        }

        assertEquals(1L, orderDetail.getOrderDetailId());
        assertEquals(1L, orderDetail.getOrderId());
        assertEquals(10, orderDetail.getAmount());
    }

}
