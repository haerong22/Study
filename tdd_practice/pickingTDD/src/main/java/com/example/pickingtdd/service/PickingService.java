package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.PickingList;
import com.example.pickingtdd.entity.Sku;

public interface PickingService {

    void pick(PickingList pickingList, Sku sku) throws Exception;
}
