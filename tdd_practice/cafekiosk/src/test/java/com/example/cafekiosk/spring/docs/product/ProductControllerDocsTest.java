package com.example.cafekiosk.spring.docs.product;

import com.example.cafekiosk.spring.api.controller.product.ProductController;
import com.example.cafekiosk.spring.api.controller.product.request.ProductCreateRequest;
import com.example.cafekiosk.spring.api.service.product.ProductService;
import com.example.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import com.example.cafekiosk.spring.api.service.product.response.ProductResponse;
import com.example.cafekiosk.spring.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.example.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static com.example.cafekiosk.spring.domain.product.ProductType.HANDMADE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerDocsTest extends RestDocsSupport {

    private final ProductService productService = mock(ProductService.class);

    @Override
    protected Object initController() {
        return new ProductController(productService);
    }

    @Test
    @DisplayName("신규 상품을 등록하는 API")
    void createProduct() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        given(productService.createProduct(any(ProductCreateServiceRequest.class)))
                .willReturn(
                        ProductResponse.builder()
                                .id(1L)
                                .productNumber("001")
                                .type(HANDMADE)
                                .sellingStatus(SELLING)
                                .name("아메리카노")
                                .price(4000)
                                .build()
                );

        mockMvc.perform(
                        post("/api/v1/products/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("product-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("type").type(STRING).optional().description("상품 타입"),
                                fieldWithPath("sellingStatus").type(STRING).description("상품 판매상태"),
                                fieldWithPath("name").type(STRING).description("상품 이름"),
                                fieldWithPath("price").type(NUMBER).description("상품 가격")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER).description("코드"),
                                fieldWithPath("status").type(STRING).description("상태"),
                                fieldWithPath("message").type(STRING).description("메시지"),
                                fieldWithPath("data").type(OBJECT).description("응답데이터"),
                                fieldWithPath("data.id").type(NUMBER).description("상품 ID"),
                                fieldWithPath("data.productNumber").type(STRING).description("상품 번호"),
                                fieldWithPath("data.type").type(STRING).description("상품 타입"),
                                fieldWithPath("data.sellingStatus").type(STRING).description("상품 판매상태"),
                                fieldWithPath("data.name").type(STRING).description("상품 이름"),
                                fieldWithPath("data.price").type(NUMBER).description("상품 가격")
                        )
                ));
    }
}
