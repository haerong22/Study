package com.example.order.infrastructure.order;

import com.example.order.domain.order.item.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}