package _03_stream;

import java.util.Arrays;
import java.util.List;

public class StreamEx_11 {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);

        Integer sum = numbers.stream().reduce(0, Integer::sum);
        System.out.println("sum = " + sum);

        numbers.stream().reduce(Math::min)
                .ifPresent(System.out::println);

        numbers.stream().reduce(Math::max)
                .ifPresent(System.out::println);

        List<String> strings = Arrays.asList("a", "b", "c");

        strings.stream()
                .filter(str -> !str.equals("b"))
                .reduce((a, b) -> a + b)
                .ifPresent(System.out::println);
    }
}
