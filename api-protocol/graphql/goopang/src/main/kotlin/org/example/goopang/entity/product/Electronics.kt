package org.example.goopang.entity.product

data class Electronics(
    override val id: String,
    override val name: String,
    override val price: Double,
    val warrantyPeriod: String,
) :Product {
    override val productType: ProductType = ProductType.ELECTRONICS
}
