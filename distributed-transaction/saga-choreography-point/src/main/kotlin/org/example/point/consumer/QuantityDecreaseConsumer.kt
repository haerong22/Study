package org.example.point.consumer

import org.example.point.application.PointService
import org.example.point.application.dto.PointUseCancelCommand
import org.example.point.application.dto.PointUseCommand
import org.example.point.consumer.dto.QuantityDecreasedEvent
import org.example.point.infrastructure.kafka.PointUseFailProducer
import org.example.point.infrastructure.kafka.PointUsedProducer
import org.example.point.infrastructure.kafka.dto.PointUseFailEvent
import org.example.point.infrastructure.kafka.dto.PointUsedEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class QuantityDecreaseConsumer(
    private val pointService: PointService,
    private val pointUsedProducer: PointUsedProducer,
    private val pointUseFailProducer: PointUseFailProducer,
) {

    @KafkaListener(
        topics = ["quantity-decreased"],
        groupId = "quantity-decreased-consumer",
        properties = [
            "spring.json.value.default.type=org.example.point.consumer.dto.QuantityDecreasedEvent"
        ]
    )
    fun handle(event: QuantityDecreasedEvent) {
        val requestId = event.orderId.toString()
        try {
            pointService.use(PointUseCommand(requestId, 1L, event.totalPrice))

            pointUsedProducer.send(PointUsedEvent(event.orderId))
        } catch (e: Exception) {
            pointService.cancel(PointUseCancelCommand(requestId))

            pointUseFailProducer.send(PointUseFailEvent(event.orderId))
        }
    }
}