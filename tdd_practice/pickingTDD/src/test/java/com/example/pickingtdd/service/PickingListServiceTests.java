package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.*;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PickingListServiceTests {

    @InjectMocks
    PickingListService pickingListService = new PickingListServiceImpl();

    @Mock
    OrderService orderService;

    @Spy
    PickerService pickerService = new PickerServiceImpl();

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
        // mock
        Mockito.when(orderService.changeOrderState(Mockito.any(), Mockito.any()))
                .thenReturn(true);

        PickingList assertPickingList = new PickingList();
        assertPickingList.setOrder(order);
        assertPickingList.setSkuAmountMap(
                Maps.newHashMap(
                        order.getOrderDetailList().get(0).getSku(),
                        order.getOrderDetailList().get(0).getAmount()));
        assertPickingList.setState(PickingStateEnum.NOTASSIGNED);
        assertPickingList.setPicker(new Picker());

        PickingList pickingList = pickingListService.createPickingList(order);

        assertEquals(assertPickingList.getOrder(), pickingList.getOrder());
        assertEquals(assertPickingList.getState(), pickingList.getState());
        assertEquals(
                assertPickingList.getSkuAmountMap().get(order.getOrderDetailList().get(0).getSku()),
                pickingList.getSkuAmountMap().get(order.getOrderDetailList().get(0).getSku()));
    }

    @Test
    void assignPicker() {
         PickingList pickingList = pickingListService.createPickingList(order);

         Picker picker = new Picker();

         PickingList assignedPickingList = pickingListService.assignPicker(pickingList, picker);

         assertEquals(picker, assignedPickingList.getPicker());
         assertEquals(PickingStateEnum.ASSIGNED, assignedPickingList.getState());
         assertNotNull(assignedPickingList.getPicker().getAssignedPickingList());
    }
}
