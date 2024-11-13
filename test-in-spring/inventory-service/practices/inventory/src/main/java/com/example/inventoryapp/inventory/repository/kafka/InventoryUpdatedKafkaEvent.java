package com.example.inventoryapp.inventory.repository.kafka;

import com.example.inventoryapp.inventory.service.event.InventoryUpdatedEvent;
import org.jetbrains.annotations.NotNull;

import static com.example.inventoryapp.inventory.repository.kafka.InventoryKafkaEventType.INVENTORY_UPDATED;

public record InventoryUpdatedKafkaEvent(
        @NotNull InventoryKafkaEventType type,
        @NotNull String itemId,
        @NotNull Long stock
) implements InventoryKafkaEvent {

    public static InventoryUpdatedKafkaEvent from(InventoryUpdatedEvent updatedEvent) {
        return new InventoryUpdatedKafkaEvent(
                INVENTORY_UPDATED,
                updatedEvent.itemId(),
                updatedEvent.stock()
        );
    }
}
