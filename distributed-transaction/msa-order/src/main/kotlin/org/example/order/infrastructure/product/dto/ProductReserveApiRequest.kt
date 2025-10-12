package org.example.order.infrastructure.product.dto

data class ProductReserveApiRequest(
    val requestId: String,
    val items: List<ReserveItem>
) {

    data class ReserveItem(
        val productId: Long,
        val reserveQuantity: Long,
    )
}