package org.example.product.consumer

import org.example.product.application.ProductService
import org.example.product.application.dto.ProductBuyCancelCommand
import org.example.product.application.dto.ProductBuyCommand
import org.example.product.consumer.dto.OrderPlacedEvent
import org.example.product.infrastructure.kafka.QuantityDecreasedFailProducer
import org.example.product.infrastructure.kafka.QuantityDecreasedProducer
import org.example.product.infrastructure.kafka.dto.QuantityDecreasedEvent
import org.example.product.infrastructure.kafka.dto.QuantityDecreasedFailEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class OrderPlacedConsumer(
    private val productService: ProductService,
    private val quantityDecreasedProducer: QuantityDecreasedProducer,
    private val quantityDecreasedFailProducer: QuantityDecreasedFailProducer,
) {

    @KafkaListener(
        topics = ["order-placed"],
        groupId = "order-placed-consumer",
        properties = [
            "spring.json.value.default.type=org.example.product.consumer.dto.OrderPlacedEvent"
        ]
    )
    fun handle(event: OrderPlacedEvent) {
        val requestId = event.orderId.toString()

        try {
            val cmd = ProductBuyCommand(
                requestId,
                event.productInfos.map { ProductBuyCommand.ProductInfo(it.productId, it.quantity) }
            )

            val result = productService.buy(cmd)

            val event = QuantityDecreasedEvent(event.orderId, result.totalPrice)
            quantityDecreasedProducer.send(event)
        } catch (ex: Exception) {
            val cmd = ProductBuyCancelCommand(requestId)
            productService.cancel(cmd)

            val event = QuantityDecreasedFailEvent(event.orderId)
            quantityDecreasedFailProducer.send(event)
        }
    }
}