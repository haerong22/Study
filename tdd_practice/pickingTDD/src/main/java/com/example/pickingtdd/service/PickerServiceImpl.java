package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.Picker;
import com.example.pickingtdd.entity.PickerStateEnum;
import com.example.pickingtdd.entity.PickingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PickerServiceImpl implements PickerService {

    @Autowired
    PickingListService pickingListService;

    @Override
    public Picker assignPickingList(Picker picker, PickingList pickingList) {
        picker.setAssignedPickingList(pickingList);
        picker.setAssignedOrder(pickingList.getOrder());
        picker.setState(PickerStateEnum.ASSIGNED);

        if (pickingList.getPicker() == null || !picker.equals(pickingList.getPicker())) {
            pickingListService.assignPicker(pickingList, picker);
        }
        return picker;
    }
}
