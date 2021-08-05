package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.Order;
import com.example.pickingtdd.entity.Picker;
import com.example.pickingtdd.entity.PickingList;

public interface PickingListService {

    PickingList createPickingList(Order order);

    PickingList assignPicker(PickingList pickingList, Picker picker);
}
