package com.example.inventoryapp.inventory.repository.kafka;

public sealed interface InventoryKafkaEvent
        permits InventoryDecreasedKafkaEvent, InventoryUpdatedKafkaEvent {

    String itemId();
}
