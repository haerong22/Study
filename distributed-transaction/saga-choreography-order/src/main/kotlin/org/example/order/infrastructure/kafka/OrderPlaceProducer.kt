package org.example.order.infrastructure.kafka

import org.example.order.infrastructure.kafka.dto.OrderPlacedEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class OrderPlaceProducer(
    private val kafkaTemplate: KafkaTemplate<String, OrderPlacedEvent>
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun send(event: OrderPlacedEvent) {
        log.info("New order placed event: $event")
        kafkaTemplate.send(
            "order-placed",
            event.orderId.toString(),
            event
        )
    }
}