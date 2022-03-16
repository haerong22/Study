package _03_stream;

import java.util.stream.Stream;

public class StreamEx_04 {

    public static void main(String[] args) {

        Stream<String> stringStream = Stream.of("aa", "bb", "cc", "dd", "ee");
        int sum = stringStream.parallel() // 병렬 스트림으로 전환
                .mapToInt(String::length).sum();
        System.out.println("sum = " + sum);

    }
}
