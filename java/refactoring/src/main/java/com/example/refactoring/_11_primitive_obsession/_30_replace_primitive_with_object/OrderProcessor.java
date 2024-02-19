package com.example.refactoring._11_primitive_obsession._30_replace_primitive_with_object;

import java.util.List;
import java.util.Objects;

public class OrderProcessor {

    public long numberOfHighPriorityOrders(List<Order> orders) {
        return orders.stream()
                .filter(o -> o.getPriority().higherThan(new Priority("normal")))
                .count();
    }
}
