package org.example.point.consumer.dto

data class QuantityDecreasedEvent(
    val orderId: Long,
    val totalPrice: Long,
)
