package com.example.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeTest {

    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지 확인한다.")
    void containsStockType() {
        // given
        ProductType givenType = ProductType.HANDMADE;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isFalse();

    }

    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지 확인한다.")
    void containsStockType2() {
        // given
        ProductType givenType = ProductType.BAKERY;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isTrue();

    }

    @ParameterizedTest
    @DisplayName("상품 타입이 재고 관련 타입인지 확인한다.(CsvSource)")
    @CsvSource({"HANDMADE, false", "BOTTLE, true", "BAKERY, true"})
    void containsStockType3(ProductType productType, boolean expected) {
        // given

        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideProductTypesForCheckingStockType() {
        return Stream.of(
                Arguments.of(ProductType.HANDMADE, false),
                Arguments.of(ProductType.BOTTLE, true),
                Arguments.of(ProductType.BAKERY, true)
        );
    }

    @ParameterizedTest
    @DisplayName("상품 타입이 재고 관련 타입인지 확인한다.(MethodSource)")
    @MethodSource("provideProductTypesForCheckingStockType")
    void containsStockType4(ProductType productType, boolean expected) {
        // given

        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }
}