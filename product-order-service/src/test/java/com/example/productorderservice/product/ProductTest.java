package com.example.productorderservice.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    void update() {
        Product product = new Product("상품명", 1000, DiscountPolicy.NONE);

        product.update("상품수정", 2000, DiscountPolicy.NONE);

        assertThat(product.getName()).isEqualTo("상품수정");
        assertThat(product.getPrice()).isEqualTo(2000);
    }

}