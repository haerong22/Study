package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderServiceTests {

    @Autowired
    OrderService orderService;

    Order orderSuccess;
    Order orderFail;

    @BeforeEach
    void init() {
        orderSuccess = new Order();
        orderSuccess.setOrderId(1L);
        orderSuccess.setState("Ordered");

        orderFail = new Order();
        orderFail.setOrderId(null);
        orderFail.setState("");
    }

    @Test
    void createOrder_success() {
        Order order = new Order();
        try {
            order = orderService.createOrder(orderSuccess);
        } catch (Exception e) {
            // do nothing
        }

        assertEquals(1L, order.getOrderId());
        assertEquals("Ordered", order.getState());
    }

    @Test
    void orderValidation_success() {
        Order order = new Order();
        try {
            order = orderService.createOrder(orderSuccess);
        } catch (Exception e) {
            fail("should not throw exception");
        }

        assertEquals(1L, order.getOrderId());
        assertEquals("Ordered", order.getState());
    }

    @Test
    void orderValidation_fail() {
        Exception e = assertThrows(Exception.class, () -> {
            orderService.createOrder(orderFail);
        });

        assertEquals("order validation fail", e.getMessage());
    }
}
