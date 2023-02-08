package com.example.productorderservice.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductServicePojoTest {

    private ProductService productService;
    private StubProductPort productPort = new StubProductPort();

    @BeforeEach
    void setUp() {
        productService = new ProductService(productPort);
    }

    @Test
    void 상품수정() {
        final Long productId = 1L;
        final Product product = new Product("상품명", 1000, DiscountPolicy.NONE);
        final UpdateProductRequest request = new UpdateProductRequest("상품수정", 2000, DiscountPolicy.NONE);

        productPort.getProduct_will_return = product;
        productService = new ProductService(productPort);

        productService.updateProduct(productId, request);

        assertThat(product.getName()).isEqualTo("상품수정");
        assertThat(product.getPrice()).isEqualTo(2000);
    }

    private static class StubProductPort implements ProductPort {

        public Product getProduct_will_return;

        @Override
        public void save(Product product) {

        }

        @Override
        public Product getProduct(long productId) {
            return getProduct_will_return;
        }
    }
}
