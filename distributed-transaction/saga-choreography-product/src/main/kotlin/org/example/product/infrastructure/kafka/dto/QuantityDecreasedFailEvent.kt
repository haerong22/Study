package org.example.product.infrastructure.kafka.dto

data class QuantityDecreasedFailEvent(
    val orderId: Long,
)
