package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderServiceTests {

    @Autowired
    OrderService orderService;

    @Test
    void create_order_success() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setState("Ordered");

        order = orderService.createOrder(order);

        Assertions.assertEquals(1L, order.getOrderId());
        Assertions.assertEquals("Ordered", order.getState());
    }
}
