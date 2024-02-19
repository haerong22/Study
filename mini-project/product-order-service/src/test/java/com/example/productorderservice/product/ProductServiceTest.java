package com.example.productorderservice.product;

import com.example.productorderservice.product.application.service.AddProductRequest;
import com.example.productorderservice.product.application.service.GetProductResponse;
import com.example.productorderservice.product.application.service.ProductService;
import com.example.productorderservice.product.application.service.UpdateProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void 상품등록() {
        final AddProductRequest request = ProductSteps.상품등록요청_생성();

        productService.addProduct(request);
    }

    @Test
    void 상품조회() {
        // 상품등록
        productService.addProduct(ProductSteps.상품등록요청_생성());
        final long productId = 1L;

        // 상품 조회
        final GetProductResponse response = productService.getProduct(productId);

        // 상품의 응답을 검증
        assertThat(response).isNotNull();
    }

    @Test
    void 상품수정() {
        // 상품등록
        productService.addProduct(ProductSteps.상품등록요청_생성());

        final Long productId = 1L;
        final UpdateProductRequest request = ProductSteps.상품수정요청_생성();

        productService.updateProduct(productId, request);

        GetProductResponse response = productService.getProduct(productId);

        assertThat(response.name()).isEqualTo("상품수정");
        assertThat(response.price()).isEqualTo(2000);
    }

}
