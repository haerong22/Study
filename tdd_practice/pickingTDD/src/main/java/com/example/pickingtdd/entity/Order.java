package com.example.pickingtdd.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {

    private Long orderId;
    private OrderStateEnum State;
    private List<OrderDetail> orderDetailList;
}
