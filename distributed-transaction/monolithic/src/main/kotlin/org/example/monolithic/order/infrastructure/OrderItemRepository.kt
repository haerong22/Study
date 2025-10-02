package org.example.monolithic.order.infrastructure

import org.example.monolithic.order.domain.OrderItem
import org.springframework.data.jpa.repository.JpaRepository

interface OrderItemRepository : JpaRepository<OrderItem, Long> {
}