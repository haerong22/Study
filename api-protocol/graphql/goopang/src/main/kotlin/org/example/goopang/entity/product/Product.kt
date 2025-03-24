package org.example.goopang.entity.product

import org.example.goopang.entity.SearchResult

interface Product: SearchResult {
    val id: String
    val name: String
    val price: Double
    val productType: ProductType
}