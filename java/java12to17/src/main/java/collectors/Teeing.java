package collectors;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Teeing {

    public static void main(String[] args) {

        List<Fruit> fruits = List.of(
                new Fruit("사과", 100),
                new Fruit("바나나", 200),
                new Fruit("파인애플", 300),
                new Fruit("수박", 400)
        );

        fruits.stream()
                .collect(Collectors.teeing(
                        Collectors.minBy(Comparator.comparingInt(Fruit::price)),
                        Collectors.maxBy(Comparator.comparingInt(Fruit::price)),
                        (f1, f2) -> {
                            f1.ifPresent(f -> System.out.printf("가장 싼 과일은 %s 입니다.\n", f.name()));
                            f1.ifPresent(f -> System.out.printf("가장 비싼 과일은 %s 입니다.\n", f.name()));
                            return 0;
                        }
                ));
    }
}
