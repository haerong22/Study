package com.example.productorderservice.order;

import com.example.productorderservice.product.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderPort orderPort;

    OrderService(OrderPort orderPort) {
        this.orderPort = orderPort;
    }

    @Transactional
    public void createOrder(CreateOrderRequest request) {
        Product product = orderPort.getProductById(request.productId());

        Order order = new Order(product, request.quantity());

        orderPort.save(order);
    }
}
