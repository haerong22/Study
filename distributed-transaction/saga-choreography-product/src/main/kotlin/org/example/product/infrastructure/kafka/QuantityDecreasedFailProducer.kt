package org.example.product.infrastructure.kafka

import org.example.product.infrastructure.kafka.dto.QuantityDecreasedFailEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class QuantityDecreasedFailProducer(
    private val kafkaTemplate: KafkaTemplate<String, QuantityDecreasedFailEvent>,
) {

    fun send(event: QuantityDecreasedFailEvent) {
        kafkaTemplate.send(
            "quantity-decreased-fail",
            event.orderId.toString(),
            event
        )
    }
}