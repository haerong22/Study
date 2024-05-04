package com.bobby.api.feature;

import java.util.Optional;
import java.util.stream.Stream;

public class OptionalFeature {

    public void ifPresentOrElse(Optional<String> str) {
        str.ifPresentOrElse(
                s -> System.out.println("값이 있으면 출력: " + s),
                () -> System.out.println("값이 없으면 출력")
        );
    }

    public void or(Optional<Integer> num) {
        Optional<Integer> optional = num.or(() -> Optional.of(3));
    }

    public void stream(Optional<Integer> num) {
        Stream<Integer> stream = num.stream();
    }
}
