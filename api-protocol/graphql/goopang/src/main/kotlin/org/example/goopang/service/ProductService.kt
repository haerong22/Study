package org.example.goopang.service

import org.apache.coyote.BadRequestException
import org.example.goopang.entity.product.Product
import org.example.goopang.repository.Database
import org.springframework.stereotype.Service

@Service
class ProductService {

    fun getProduct(productId: String): Product {
        return Database.products.firstOrNull { it.id == productId }
            ?: throw BadRequestException("Product not found")
    }
}