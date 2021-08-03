package com.example.inventorytdd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryHelperTests {

    @Test
    void inventoryHelper_getUsableCapa_test() {
        InventoryHelper inventoryHelper = new InventoryHelper();

        Inventory inventory = new Inventory();
        inventory.setCapacity(10);
        inventory.setCurrent(5);

        int usableCapa = inventoryHelper.getUsableCapa(inventory);
        assertEquals(5, usableCapa);
    }

    @Test
    void inventoryHelper_inbound_true() {
        InventoryHelper inventoryHelper = new InventoryHelper();

        Inventory inventory = new Inventory();
        inventory.setCapacity(40);

        assertTrue(inventoryHelper.inbound(inventory, 10));
        assertEquals(10, inventory.getCurrent());
    }

    @Test
    void inventoryHelper_inbound_false() {
        InventoryHelper inventoryHelper = new InventoryHelper();

        Inventory inventory = new Inventory();
        inventory.setCapacity(10);

        assertFalse(inventoryHelper.inbound(inventory, 40));
        assertEquals(0, inventory.getCurrent());
    }

    @Test
    void inventoryHelper_isInboundable_true() {
        InventoryHelper inventoryHelper = new InventoryHelper();
        Inventory inventory = new Inventory();
        inventory.setCapacity(10);
        inventory.setCurrent(5);

        assertTrue(inventoryHelper.isInboundable(inventory, 3));
    }

    @Test
    void inventoryHelper_isInboundable_false() {
        InventoryHelper inventoryHelper = new InventoryHelper();
        Inventory inventory = new Inventory();
        inventory.setCapacity(10);
        inventory.setCurrent(5);

        assertFalse(inventoryHelper.isInboundable(inventory, 10));
    }

    @Test
    void inventoryHelper_createInventory() {
        InventoryHelper inventoryHelper = new InventoryHelper();
        Inventory inventory = inventoryHelper.createInventory();

        assertNotNull(inventory);
    }
}
