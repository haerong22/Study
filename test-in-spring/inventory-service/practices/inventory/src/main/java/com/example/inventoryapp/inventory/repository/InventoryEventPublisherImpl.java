package com.example.inventoryapp.inventory.repository;

import com.example.inventoryapp.inventory.repository.kafka.InventoryDecreasedKafkaEvent;
import com.example.inventoryapp.inventory.repository.kafka.InventoryKafkaEvent;
import com.example.inventoryapp.inventory.repository.kafka.InventoryUpdatedKafkaEvent;
import com.example.inventoryapp.inventory.service.event.InventoryDecreasedEvent;
import com.example.inventoryapp.inventory.service.event.InventoryEvent;
import com.example.inventoryapp.inventory.service.event.InventoryEventPublisher;
import com.example.inventoryapp.inventory.service.event.InventoryUpdatedEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventPublisherImpl implements InventoryEventPublisher {
    private final StreamBridge streamBridge;

    public InventoryEventPublisherImpl(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void publish(InventoryEvent event) {
        final InventoryKafkaEvent kafkaEvent = createFromDomainEvent(event);

        final Message<InventoryKafkaEvent> message = MessageBuilder.withPayload(kafkaEvent)
                .setHeader(MESSAGE_KEY, kafkaEvent.itemId())
                        .build();
        streamBridge.send("inventory-out-0", message);
    }

    private InventoryKafkaEvent createFromDomainEvent(InventoryEvent event) {
        return switch (event) {
            case InventoryDecreasedEvent decreasedEvent -> InventoryDecreasedKafkaEvent.from(decreasedEvent);
            case InventoryUpdatedEvent updatedEvent -> InventoryUpdatedKafkaEvent.from(updatedEvent);
        };

    }
}
