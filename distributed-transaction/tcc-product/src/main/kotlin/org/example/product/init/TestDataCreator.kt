package org.example.product.init

import jakarta.annotation.PostConstruct
import org.example.product.domain.Product
import org.example.product.infrastructure.ProductRepository
import org.springframework.stereotype.Component

@Component
class TestDataCreator(
    private val productRepository: ProductRepository,
) {

    @PostConstruct
    fun createTestData() {
        productRepository.save(Product(100L, 100L, 0))
        productRepository.save(Product(200L, 100L, 0))
    }
}