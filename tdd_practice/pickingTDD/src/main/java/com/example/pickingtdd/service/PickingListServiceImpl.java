package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PickingListServiceImpl implements PickingListService {

    @Autowired
    OrderService orderService;

    @Override
    public PickingList createPickingList(Order order) {
        PickingList pickingList = new PickingList();
        pickingList.setOrder(order);
        pickingList.setState(PickingStateEnum.NOTASSIGNED);

        Map<Sku, Integer> skuAmountMap = new HashMap<>();
        for (OrderDetail orderDetail : order.getOrderDetailList()) {
            skuAmountMap.put(orderDetail.getSku(), orderDetail.getAmount());
        }

        pickingList.setSkuAmountMap(skuAmountMap);

        // Order State Change
        orderService.changeOrderState(order, OrderStateEnum.LISTED);
        return pickingList;
    }

    @Override
    public PickingList assignPicker(PickingList pickingList, Picker picker) {
        pickingList.setPicker(picker);
        pickingList.setState(PickingStateEnum.ASSIGNED);

        orderService.changeOrderState(pickingList.getOrder(), OrderStateEnum.ASSIGNED);
        return pickingList;
    }
}
