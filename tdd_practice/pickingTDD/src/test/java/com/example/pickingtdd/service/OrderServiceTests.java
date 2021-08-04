package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.Order;
import com.example.pickingtdd.entity.OrderDetail;
import com.example.pickingtdd.entity.OrderStateEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class OrderServiceTests {

    @InjectMocks
    OrderService orderService = new OrderServiceImpl();

    @Mock
    OrderDetailService orderDetailService;

    Order orderSuccess;
    Order orderFail;

    @BeforeEach
    void init() {
        OrderDetail orderDetail = new OrderDetail();
        orderSuccess = new Order();
        orderSuccess.setOrderId(1L);
        orderSuccess.setState(OrderStateEnum.ORDERED);
        orderSuccess.setOrderDetailList(Collections.singletonList(orderDetail));

        orderFail = new Order();
        orderFail.setOrderId(null);
        orderFail.setState(null);
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
        assertEquals(OrderStateEnum.ORDERED, order.getState());
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
        assertEquals(OrderStateEnum.ORDERED, order.getState());
    }

    @Test
    void orderValidation_fail() {
        Exception e = assertThrows(Exception.class, () -> {
            orderService.createOrder(orderFail);
        });

        assertEquals("order validation fail", e.getMessage());
    }

    @Test
    void changeOrderState() {
        orderService.changeOrderState(orderSuccess, OrderStateEnum.LISTED);

        assertEquals(OrderStateEnum.LISTED, orderSuccess.getState());
    }
}
