package org.example.monolithic.init

import jakarta.annotation.PostConstruct
import org.example.monolithic.point.domain.Point
import org.example.monolithic.point.infrastructure.PointRepository
import org.example.monolithic.product.domain.Product
import org.example.monolithic.product.infrastructure.ProductRepository
import org.springframework.stereotype.Component

@Component
class TestDataCreator(
    private val pointRepository: PointRepository,
    private val productRepository: ProductRepository,
) {

    @PostConstruct
    fun createTestData() {
        pointRepository.save(Point(1L, 10000L))
        productRepository.save(Product(100L, 100L))
        productRepository.save(Product(100L, 200L))
    }
}