package com.example.rental.domain.model.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LateFeeTest {

    @Test
    @DisplayName("연체료를 생성하면 0포인트로 생성된다.")
    void createLateFee() {
        // given

        // when
        LateFee result = LateFee.createLateFee();

        // then
        assertThat(result.getPoint()).isEqualTo(0);
    }

    @Test
    @DisplayName("연체 포인트를 증가할 수 있다.")
    void addPoint() {
        // given
        LateFee lateFee = LateFee.createLateFee();

        // when
        LateFee result = lateFee.addPoint(100);

        // then
        assertThat(result.getPoint()).isEqualTo(100);
    }

    @Test
    @DisplayName("연체 포인트를 삭제할 수 있다.")
    void removePoint() {
        // given
        LateFee lateFee = LateFee.createLateFee().addPoint(100);

        // when
        LateFee result = lateFee.removePoint(50);

        // then
        assertThat(result.getPoint()).isEqualTo(50);
    }

    @Test
    @DisplayName("연체 포인트보다 큰 값은 삭제할 수 없다.")
    void removePointFail() {
        // given
        LateFee lateFee = LateFee.createLateFee();
        lateFee.addPoint(100);

        // when

        // then
        assertThatThrownBy(() -> lateFee.removePoint(200))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("보유한 포인트보다 커서 삭제할 수 없습니다.");
    }

}