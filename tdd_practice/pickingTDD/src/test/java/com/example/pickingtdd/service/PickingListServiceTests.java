package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.*;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PickingListServiceTests {

    @Autowired
    PickingListService pickingListService;

    Order order;

    @BeforeEach
    void orderSetup() {
        order = new Order();
        order.setOrderId(1L);
        order.setState(OrderStateEnum.ORDERED);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailId(1L);
        orderDetail.setOrderId(1L);
        orderDetail.setAmount(10);
        orderDetail.setSku(new Sku());

        order.setOrderDetailList(Collections.singletonList(orderDetail));
    }

    @Test
    void createPickingList() {
        PickingList assertPickingList = new PickingList();
        assertPickingList.setOrderId(1L);
        assertPickingList.setSkuAmountMap(
                Maps.newHashMap(
                        order.getOrderDetailList().get(0).getSku(),
                        order.getOrderDetailList().get(0).getAmount()));
        assertPickingList.setState(PickingStateEnum.NOTASSIGNED);
        assertPickingList.setPicker(new Picker());

        PickingList pickingList = pickingListService.createPickingList(order);

        assertEquals(assertPickingList.getOrderId(), pickingList.getOrderId());
        assertEquals(PickingStateEnum.NOTASSIGNED, pickingList.getState());
        assertEquals(
                assertPickingList.getSkuAmountMap().get(order.getOrderDetailList().get(0).getSku()),
                pickingList.getSkuAmountMap().get(order.getOrderDetailList().get(0).getSku()));
    }
}
