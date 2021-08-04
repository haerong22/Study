package com.example.pickingtdd.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PickingList {
    private Long id;
    private Long orderId;
    private Map<Sku, Integer> skuAmountMap;
    private PickingStateEnum state;
    private Picker picker;
}
