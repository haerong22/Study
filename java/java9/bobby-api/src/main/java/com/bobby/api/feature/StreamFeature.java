package com.bobby.api.feature;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamFeature {

    public void takeWhile() {
        List<Integer> list = Stream.of(10, 5, 15, 3, 20)
                .takeWhile(num -> num <= 10)
                .collect(Collectors.toList());
    }

    public void dropWhile() {
        List<Integer> list = Stream.of(10, 5, 15, 3, 20)
                .dropWhile(num -> num <= 10)
                .collect(Collectors.toList());
    }

    public void ofNullable() {
        Stream.ofNullable("ABC");
    }

    public void iterate() {
        Stream.iterate(0, i -> i < 10, i -> i + 2)
                .forEach(System.out::println);
    }
}
