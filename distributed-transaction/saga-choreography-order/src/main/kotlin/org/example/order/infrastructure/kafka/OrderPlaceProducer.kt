package org.example.order.infrastructure.kafka

import org.example.order.infrastructure.kafka.dto.OrderPlacedEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class OrderPlaceProducer(
    private val kafkaTemplate: KafkaTemplate<String, OrderPlacedEvent>
) {

    fun send(event: OrderPlacedEvent) {
        kafkaTemplate.send(
            "order-placed",
            event.orderId.toString(),
            event
        )
    }
}