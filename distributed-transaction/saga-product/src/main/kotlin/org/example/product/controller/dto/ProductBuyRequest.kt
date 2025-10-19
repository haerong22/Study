package org.example.product.controller.dto

import org.example.product.application.dto.ProductBuyCommand

data class ProductBuyRequest(
    val requestId: String,
    val productInfos: List<ProductInfo>,
) {
    fun toCommand() = ProductBuyCommand(
        requestId,
        productInfos.map { ProductBuyCommand.ProductInfo(it.productId, it.quantity) }
    )

    data class ProductInfo(
        val productId: Long,
        val quantity: Long,
    )
}
