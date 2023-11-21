package com.example.cafekiosk.spring.domain.order;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductRepository;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static com.example.cafekiosk.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles("test")
@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("원하는 날짜와 상태로 주문 리스트를 불러온다.")
    void findOrdersBy() {
        // given
        Product product = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        productRepository.save(product);

        Order order1 = Order.create(List.of(product), LocalDateTime.of(2023, 11, 20, 10, 0));
        Order order2 = Order.create(List.of(product), LocalDateTime.of(2023, 11, 21, 10, 0));
        Order order3 = Order.create(List.of(product), LocalDateTime.of(2023, 11, 22, 10, 0));

        orderRepository.saveAll(List.of(order1, order2, order3));

        // when
        List<Order> result = orderRepository.findOrdersBy(
                LocalDateTime.of(2023, 11, 20, 0, 0),
                LocalDateTime.of(2023, 11, 21, 0, 0),
                OrderStatus.INIT
        );

        // then
        assertThat(result).hasSize(1)
                .extracting("orderStatus", "totalPrice", "registeredDateTime")
                .containsExactlyInAnyOrder(
                        tuple(OrderStatus.INIT, 4000, LocalDateTime.of(2023, 11, 20, 10, 0))
                );
    }

    private Product createProduct(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }

}