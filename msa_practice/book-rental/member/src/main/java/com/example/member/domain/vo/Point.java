package com.example.member.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Point {

    private final long pointValue ;

    public static Point create(long point) {
        if (point < 0) {
            throw new IllegalArgumentException("포인트는 양수만 가능합니다.");
        }

        return new Point(point);
    }

    public Point addPoint(long point) {
        return new Point(pointValue + point);
    }

    public Point removePoint(long point) {
        if (point > this.pointValue) {
            throw new IllegalArgumentException("기존 보유 포인트보다 적어 삭제할 수 없습니다.");
        }
        return new Point(this.pointValue - point);
    }
}
