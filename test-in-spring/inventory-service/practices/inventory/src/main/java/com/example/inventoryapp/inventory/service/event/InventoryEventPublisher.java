package com.example.inventoryapp.inventory.service.event;

public interface InventoryEventPublisher {

    void publish(InventoryEvent event);
}
