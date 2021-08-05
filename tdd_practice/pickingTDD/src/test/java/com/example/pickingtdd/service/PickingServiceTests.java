package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.*;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class PickingServiceTests {

    @Autowired PickingService pickingService;

    Order order;
    PickingList pickingList;
    OrderDetail orderDetail;
    OrderDetail orderDetail2;
    Picker picker;
    
    @BeforeEach
    void init() {
        order = new Order();
        order.setState(OrderStateEnum.ASSIGNED);
        order.setOrderId(1L);

        orderDetail = new OrderDetail();
        orderDetail.setOrderDetailId(1L);
        orderDetail.setOrderId(1L);
        orderDetail.setSku(new Sku());
        orderDetail.setAmount(10);

        orderDetail2 = new OrderDetail();
        orderDetail2.setOrderDetailId(1L);
        orderDetail2.setOrderId(1L);
        orderDetail2.setSku(new Sku());
        orderDetail2.setAmount(10);

        order.setOrderDetailList(Arrays.asList(orderDetail, orderDetail2));

        pickingList = new PickingList();
        pickingList.setOrder(order);
        pickingList.setState(PickingStateEnum.ASSIGNED);
        pickingList.setId(1L);
        pickingList.setSkuAmountMap(Maps.newHashMap(orderDetail.getSku(), orderDetail.getAmount()));
        pickingList.getSkuAmountMap().put(orderDetail2.getSku(), orderDetail2.getAmount());
        pickingList.setPickedMap(Maps.newHashMap(orderDetail.getSku(), 0));
        pickingList.getPickedMap().put(orderDetail2.getSku(), 0);

        picker = new Picker();
        picker.setPickerId(1L);
        picker.setAssignedPickingList(pickingList);
        picker.setAssignedOrder(order);

        pickingList.setPicker(picker);
    }
    
    @Test
    void pick_one_success() {

        try {
            pickingService.pick(pickingList, orderDetail.getSku());
        } catch (Exception e) {
            fail("should not exception");
        }

        assertEquals(PickingStateEnum.PROGRESS, pickingList.getState());
        assertEquals(PickerStateEnum.PROCESS, picker.getState());
        assertEquals(1, pickingList.getPickedMap().get(orderDetail.getSku()));
    }

    @Test
    void pick_DONE_success() {
        try {
            for (int i = 0; i < 10; i++) {
                pickingService.pick(pickingList, orderDetail.getSku());
                pickingService.pick(pickingList, orderDetail2.getSku());
            }
        } catch (Exception e) {
            fail("should not exception");
        }

        assertEquals(PickingStateEnum.DONE, pickingList.getState());
        assertEquals(PickerStateEnum.DONE, pickingList.getPicker().getState());
    }

    @Test
    void pick_wrongSku() {
        try {
            pickingService.pick(pickingList, new Sku());
        } catch (Exception e) {
            assertEquals("wrong sku", e.getMessage());
        }
    }

    @Test
    void pick_toMuch() {
        try {
            pickingService.pick(pickingList, orderDetail.getSku());
        } catch (Exception e) {
            assertEquals("to much sku", e.getMessage());
        }
    }
}
