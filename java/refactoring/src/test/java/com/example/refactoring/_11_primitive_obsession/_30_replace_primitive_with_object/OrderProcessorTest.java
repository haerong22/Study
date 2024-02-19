package com.example.refactoring._11_primitive_obsession._30_replace_primitive_with_object;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderProcessorTest {

    @Test
    void numberOfHighPriorityOrders() {
        OrderProcessor orderProcessor = new OrderProcessor();
        long highPriorityOrders = orderProcessor.numberOfHighPriorityOrders(
                List.of(new Order("low"),
                        new Order("normal"),
                        new Order("high"),
                        new Order("rush")));
        assertEquals(2, highPriorityOrders);
    }

}