package com.example.inventoryapp.inventory.service.event;

public sealed interface InventoryEvent
        permits InventoryDecreasedEvent, InventoryUpdatedEvent {
}
