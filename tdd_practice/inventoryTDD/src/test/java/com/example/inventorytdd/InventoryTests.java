package com.example.inventorytdd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTests {

    @Test
    void inventory_size_test() {
        Inventory inventory = new Inventory();

        inventory.setLength(10);
        inventory.setWidth(10);
        inventory.setHeight(10);

        assertEquals(10, inventory.getLength());
        assertEquals(10, inventory.getWidth());
        assertEquals(10, inventory.getHeight());
    }

    @Test
    void inventory_type_test() {
        Inventory inventory = new Inventory();
        inventory.setType(InventoryTypeEnum.COLD);

        assertEquals(InventoryTypeEnum.COLD, inventory.getType());
    }
    @Test
    void inventory_capacity_test() {
        Inventory inventory = new Inventory();
        inventory.setCapacity(10);

        assertEquals(10, inventory.getCapacity());
    }

    @Test
    void inventory_current_test() {
        Inventory inventory = new Inventory();
        inventory.setCurrent(5);

        assertEquals(5, inventory.getCurrent());
    }
}
