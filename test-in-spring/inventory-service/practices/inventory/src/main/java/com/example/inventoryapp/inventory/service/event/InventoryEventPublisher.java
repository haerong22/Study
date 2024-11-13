package com.example.inventoryapp.inventory.service.event;

public interface InventoryEventPublisher {
    String MESSAGE_KEY = "custom_messageKey";

    void publish(InventoryEvent event);
}
