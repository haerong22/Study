package org.example.monolithic.order.application.dto

data class PlaceOrderCommand(
    val orderItems: List<OrderItem>,
) {

    data class OrderItem(
        val productId: Long,
        val quantity: Long,
    )
}