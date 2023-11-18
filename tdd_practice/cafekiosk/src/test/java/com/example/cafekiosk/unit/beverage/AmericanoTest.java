package com.example.cafekiosk.unit.beverage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AmericanoTest {

    @Test
    @DisplayName("객체를 생성하면 음료의 이름을 가져올 수 있다.")
    void getName() {
        // given

        // when
        Americano americano = new Americano();

        // then
        assertThat(americano.getName()).isEqualTo("아메리카노");
    }

    @Test
    @DisplayName("객체를 생성하면 음료의 가격을 가져올 수 있다.")
    void getPrice() {
        // given

        // when
        Americano americano = new Americano();

        // then
        assertThat(americano.getPrice()).isEqualTo(4000);
    }

}