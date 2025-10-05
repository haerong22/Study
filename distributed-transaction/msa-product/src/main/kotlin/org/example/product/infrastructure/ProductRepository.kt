package org.example.product.infrastructure

import org.example.product.domain.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<Product, Long> {
}