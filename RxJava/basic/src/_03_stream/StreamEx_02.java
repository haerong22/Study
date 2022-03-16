package _03_stream;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamEx_02 {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        // 스트림 생성
        Stream<Integer> stream = list.stream(); // Collection
        Stream<String> stringStream = Stream.of("a", "b", "c");// 배열
        Stream<Double> ranStream = Stream.generate(Math::random); // 메소드 레퍼런스
        IntStream intStream = new Random().ints(5); // 난수 스트림
    }
}
