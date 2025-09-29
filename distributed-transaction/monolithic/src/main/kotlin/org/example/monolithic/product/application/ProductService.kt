package org.example.monolithic.product.application

import org.example.monolithic.product.infrastructure.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {

    fun buy(productId: Long, quantity: Long): Long {
        val product = productRepository.findById(productId).orElseThrow { throw RuntimeException("데이터가 존재하지 않습니다.") }

        val totalPrice = product.calculatePrice(quantity)
        product.buy(quantity)

        productRepository.save(product)

        return totalPrice
    }
}