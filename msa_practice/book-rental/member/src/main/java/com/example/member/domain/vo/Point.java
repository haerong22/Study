package com.example.member.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Point {

    private final long pointValue ;

    public static Point create(long point) {
        return new Point(point);
    }

    public void addPoint() {

    }

    public void removePoint() {

    }
}
