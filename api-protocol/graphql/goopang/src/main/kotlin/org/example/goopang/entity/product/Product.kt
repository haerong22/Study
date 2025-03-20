package org.example.goopang.entity.product

interface Product {
    val id: String
    val name: String
    val price: Double
    val productType: ProductType
}