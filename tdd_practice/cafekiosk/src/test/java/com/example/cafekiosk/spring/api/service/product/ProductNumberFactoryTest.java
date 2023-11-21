package com.example.cafekiosk.spring.api.service.product;

import com.example.cafekiosk.spring.IntegrationTestSupport;
import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static com.example.cafekiosk.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;

class ProductNumberFactoryTest extends IntegrationTestSupport {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductNumberFactory productNumberFactory;

    @BeforeEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("마지막 상품 번호가 있으면 1추가 한 값을 리턴한다.")
    void createNextProductNumber() {
        // given
        Product product = createProduct("001");
        productRepository.save(product);

        // when
        String result = productNumberFactory.createNextProductNumber();

        // then
        assertThat(result).isEqualTo("002");
    }

    @Test
    @DisplayName("마지막 상품 번호가 null 일 경우 001을 리턴한다.")
    void createNextProductNumberWithNull() {
        // given

        // when
        String result = productNumberFactory.createNextProductNumber();

        // then
        assertThat(result).isEqualTo("001");
    }

    private Product createProduct(String productNumber) {
        return Product.builder()
                .productNumber(productNumber)
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("테스트")
                .price(1000)
                .build();
    }
}