package org.example.order.consumer

import org.example.order.application.OrderService
import org.example.order.consumer.dto.PointUsedEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PointUsedConsumer(
    private val orderService: OrderService,
) {

    @KafkaListener(
        topics = ["point-used"],
        groupId = "point-used-consumer",
        properties = [
            "spring.json.value.default.type=org.example.order.consumer.dto.PointUsedEvent"
        ]
    )
    fun handle(event: PointUsedEvent) {
        orderService.complete(event.orderId)
    }
}