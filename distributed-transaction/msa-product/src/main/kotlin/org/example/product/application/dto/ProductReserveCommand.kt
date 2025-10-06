package org.example.product.application.dto

data class ProductReserveCommand(
    val requestId: String,
    val items: List<ReserveItem>,
) {
    data class ReserveItem(
        val productId: Long,
        val reserveQuantity: Long,
    )
}