package com.example.pickingtdd.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

    private Long orderId;
    private OrderStateEnum State;
}
