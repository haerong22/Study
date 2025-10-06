package org.example.product.controller.dto

import org.example.product.application.dto.ProductReserveCommand

data class ProductReserveRequest(
    val requestId: String,
    val items: List<ReserveItem>
) {

    fun toCommand(): ProductReserveCommand {
        return ProductReserveCommand(
            requestId,
            items.map { ProductReserveCommand.ReserveItem(it.productId, it.reserveQuantity) }
        )
    }

    data class ReserveItem(
        val productId: Long,
        val reserveQuantity: Long,
    )
}