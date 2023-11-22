package com.example.rental.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LateFee {

    private final long point;

    public static LateFee createLateFee() {
        return new LateFee(0);
    }

    public LateFee addPoint(long point) {
        return new LateFee(this.point + point);
    }

    public LateFee removePoint(long point) {
        if (point > this.point) {
            throw new RuntimeException("보유한 포인트보다 커서 삭제할 수 없습니다.");
        }

        return new LateFee(this.point - point);
    }
}
