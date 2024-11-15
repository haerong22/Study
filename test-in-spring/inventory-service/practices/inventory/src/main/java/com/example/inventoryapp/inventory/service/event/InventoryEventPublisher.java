package com.example.inventoryapp.inventory.service.event;

import org.springframework.kafka.support.KafkaHeaders;

public interface InventoryEventPublisher {
    String MESSAGE_KEY = KafkaHeaders.KEY;

    void publish(InventoryEvent event);
}
