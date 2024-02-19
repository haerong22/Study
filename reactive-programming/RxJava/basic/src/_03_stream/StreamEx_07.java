package _03_stream;

import java.util.Arrays;
import java.util.List;

public class StreamEx_07 {

    public static void main(String[] args) {

        // distinct -> hashCode, equals 로 결정
        List<Integer> list = Arrays.asList(1, 2, 1, 3, 3, 2, 4);

        list.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);
    }
}
