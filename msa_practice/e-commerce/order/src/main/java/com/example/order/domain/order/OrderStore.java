package com.example.order.domain.order;

import com.example.order.domain.order.item.OrderItem;
import com.example.order.domain.order.item.OrderItemOption;
import com.example.order.domain.order.item.OrderItemOptionGroup;

public interface OrderStore {
    Order store(Order order);
    OrderItem store(OrderItem orderItem);
    OrderItemOptionGroup store(OrderItemOptionGroup orderItemOptionGroup);
    OrderItemOption store(OrderItemOption orderItemOption);
}