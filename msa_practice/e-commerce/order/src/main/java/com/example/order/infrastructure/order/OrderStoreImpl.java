package com.example.order.infrastructure.order;

import com.example.order.domain.order.Order;
import com.example.order.domain.order.OrderStore;
import com.example.order.domain.order.item.OrderItem;
import com.example.order.domain.order.item.OrderItemOption;
import com.example.order.domain.order.item.OrderItemOptionGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStoreImpl implements OrderStore {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemOptionGroupRepository orderItemOptionGroupRepository;
    private final OrderItemOptionRepository orderItemOptionRepository;

    @Override
    public Order store(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public OrderItem store(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItemOptionGroup store(OrderItemOptionGroup orderItemOptionGroup) {
        return orderItemOptionGroupRepository.save(orderItemOptionGroup);
    }

    @Override
    public OrderItemOption store(OrderItemOption orderItemOption) {
        return orderItemOptionRepository.save(orderItemOption);
    }
}