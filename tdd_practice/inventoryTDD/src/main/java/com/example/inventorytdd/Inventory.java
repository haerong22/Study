package com.example.inventorytdd;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inventory {

    private int length;
    private int width;
    private int height;
    private InventoryTypeEnum type;
    private int capacity;
    private int current;
}
