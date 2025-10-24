package org.example.order.infrastructure.product

data class ProductBuyApiRequest(
    val requestId: String,
    val productInfos: List<ProductInfo>,
) {

    data class ProductInfo(
        val productId: Long,
        val quantity: Long,
    )
}
