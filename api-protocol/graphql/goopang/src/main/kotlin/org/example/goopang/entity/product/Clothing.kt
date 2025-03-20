package org.example.goopang.entity.product

data class Clothing(
    override val id: String,
    override val name: String,
    override val price: Double,
    val size: String
) :Product {
    override val productType: ProductType = ProductType.CLOTHING
}
