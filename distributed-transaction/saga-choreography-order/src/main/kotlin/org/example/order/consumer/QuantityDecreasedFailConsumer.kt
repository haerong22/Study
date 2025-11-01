package org.example.order.consumer

import org.example.order.application.OrderService
import org.example.order.consumer.dto.QuantityDecreasedFailEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class QuantityDecreasedFailConsumer(
    private val orderService: OrderService,
) {

    @KafkaListener(
        topics = ["quantity-decreased-fail"],
        groupId = "quantity-decreased-fail-consumer",
        properties = [
            "spring.json.value.default.type=org.example.order.consumer.dto.QuantityDecreasedFailEvent"
        ]
    )
    fun handle(event: QuantityDecreasedFailEvent) {
        orderService.fail(event.orderId)
    }
}