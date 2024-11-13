package com.example.inventoryapp.inventory.repository.kafka;

import com.example.inventoryapp.inventory.service.event.InventoryDecreasedEvent;
import org.jetbrains.annotations.NotNull;

import static com.example.inventoryapp.inventory.repository.kafka.InventoryKafkaEventType.INVENTORY_DECREASED;

public record InventoryDecreasedKafkaEvent(
        @NotNull InventoryKafkaEventType type,
        @NotNull String  itemId,
        @NotNull Long quantity,
        @NotNull Long stock
) implements InventoryKafkaEvent {

    public static InventoryDecreasedKafkaEvent from(InventoryDecreasedEvent decreasedEvent) {
        return new InventoryDecreasedKafkaEvent(
                INVENTORY_DECREASED,
                decreasedEvent.itemId(),
                decreasedEvent.quantity(),
                decreasedEvent.stock()
        );
    }
}
