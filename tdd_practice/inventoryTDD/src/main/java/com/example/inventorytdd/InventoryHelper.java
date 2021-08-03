package com.example.inventorytdd;

public class InventoryHelper {

    public Inventory createInventory() {
        return new Inventory();
    }

    public int getUsableCapa(Inventory inventory) {
        return inventory.getCapacity() - inventory.getCurrent();
    }

    public boolean inbound(Inventory inventory, int count) {
        if (isInboundable(inventory, count)) {
            inventory.setCurrent(inventory.getCurrent() + count);
            return true;
        }
        return false;
    }

    public boolean isInboundable(Inventory inventory, int count) {
        return getUsableCapa(inventory) >= count;
    }
}
