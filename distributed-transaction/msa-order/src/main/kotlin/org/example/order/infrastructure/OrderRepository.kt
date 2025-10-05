package org.example.order.infrastructure

import org.example.order.domain.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository: JpaRepository<Order, Long> {
}