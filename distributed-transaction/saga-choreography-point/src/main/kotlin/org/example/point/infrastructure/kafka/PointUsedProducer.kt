package org.example.point.infrastructure.kafka

import org.example.point.infrastructure.kafka.dto.PointUsedEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class PointUsedProducer(
    private val kafkaTemplate: KafkaTemplate<String, PointUsedEvent>,
) {

    fun send(event: PointUsedEvent) {
        kafkaTemplate.send(
            "point-used",
            event.orderId.toString(),
            event
        )
    }
}