package org.example.product.consumer

import org.example.product.application.ProductService
import org.example.product.application.dto.ProductBuyCancelCommand
import org.example.product.consumer.dto.PointUseFailEvent
import org.example.product.infrastructure.kafka.QuantityDecreasedFailProducer
import org.example.product.infrastructure.kafka.dto.QuantityDecreasedFailEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PointUseFailConsumer(
    private val productService: ProductService,
    private val quantityDecreasedFailProducer: QuantityDecreasedFailProducer,
) {

    @KafkaListener(
        topics = ["point-use-fail"],
        groupId = "point-use-fail-consumer",
        properties = [
            "spring.json.value.default.type=org.example.product.consumer.dto.PointUseFailEvent"
        ]
    )
    fun handle(event: PointUseFailEvent) {
        productService.cancel(ProductBuyCancelCommand(event.orderId.toString()))
        quantityDecreasedFailProducer.send(QuantityDecreasedFailEvent(event.orderId))
    }
}
