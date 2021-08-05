package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.Picker;
import com.example.pickingtdd.entity.PickingList;

public interface PickerService {

    Picker assignPickingList(Picker picker, PickingList pickingList);
}
