package org.example.product.application.dto

data class ProductBuyCommand(
    val requestId: String,
    val productInfos: List<ProductInfo>,
) {
    data class ProductInfo(
        val productId: Long,
        val quantity: Long,
    )
}
