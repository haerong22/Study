package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.Order;
import com.example.pickingtdd.entity.Picker;
import com.example.pickingtdd.entity.PickerStateEnum;
import com.example.pickingtdd.entity.PickingList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PickerServiceTests {

    @Autowired
    PickerService pickerService;

    @Test
    void assignPickingList() {
        Picker picker = new Picker();
        picker.setPickerId(1L);
        picker.setState(PickerStateEnum.REST);

        PickingList pickingList = new PickingList();
        pickingList.setOrder(new Order());

        picker = pickerService.assignPickingList(picker, pickingList);

        assertEquals(PickerStateEnum.ASSIGNED, picker.getState());
        assertNotNull(picker.getAssignedOrder());
        assertNotNull(picker.getAssignedPickingList());
        assertNotNull(picker.getAssignedPickingList().getPicker());
    }
}
