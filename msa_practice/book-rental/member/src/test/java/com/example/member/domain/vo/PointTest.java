package com.example.member.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PointTest {

    @Test
    @DisplayName("포인트를 생성할 수 있다.")
    void create() {
        // given
        long value = 10;

        // when
        Point point = Point.create(value);

        // then
        assertThat(point.getPointValue()).isEqualTo(10);
    }

    @Test
    @DisplayName("포인트는 음수는 생성할 수 없다.")
    void createFail() {
        // given
        long value = -10;

        // when

        // then
        assertThatThrownBy(() -> Point.create(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포인트는 양수만 가능합니다.");
    }

    @Test
    @DisplayName("포인트를 추가 할 수 있다.")
    void addPoint() {
        // given
        Point point = Point.create(10);

        // when
        Point addPoint = point.addPoint(20);

        // then
        assertThat(addPoint.getPointValue()).isEqualTo(30);
    }

    @Test
    @DisplayName("포인트를 삭제할 수 있다.")
    void removePoint() {
        // given
        Point point = Point.create(10);

        // when
        Point removePoint = point.removePoint(10);

        // then
        assertThat(removePoint.getPointValue()).isEqualTo(0);
    }

    @Test
    @DisplayName("삭제요청한 포인트가 보유 포인트보다 크면 삭제할 수 없다.")
    void removePointFail() {
        // given
        Point point = Point.create(10);

        // when

        // then
        assertThatThrownBy(() -> point.removePoint(20))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기존 보유 포인트보다 적어 삭제할 수 없습니다.");
    }
}