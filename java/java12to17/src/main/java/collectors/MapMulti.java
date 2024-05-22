package collectors;

import java.util.Collection;
import java.util.List;

public class MapMulti {

    public static void main(String[] args) {

        List<List<Number>> nums = List.of(List.of(1.0, 2.0), List.of(3, 4, 5));

        List<Double> result = nums.stream()
                .flatMap(Collection::stream)
                .filter(n -> n instanceof Double)
                .map(n -> (double) n)
                .toList();

        List<Double> result2 = nums.stream()
                .<Double>mapMulti((list, consumer) -> {
                    for (Number num : list) {
                        if (num instanceof Double) {
                            consumer.accept((double) num);
                        }
                    }
                })
                .toList();
    }
}
