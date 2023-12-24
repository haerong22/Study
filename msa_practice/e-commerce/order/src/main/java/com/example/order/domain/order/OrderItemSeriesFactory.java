package com.example.order.domain.order;

import com.example.order.domain.order.item.OrderItem;

import java.util.List;

public interface OrderItemSeriesFactory {
    List<OrderItem> store(Order order, OrderCommand.RegisterOrder requestOrder);
}