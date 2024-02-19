package _03_stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamEx_03 {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(3, 1, 5, 4, 2);
        List<Integer> sortedList = list.stream()
                .sorted()  // 정렬
                .toList(); // 새로운 List 에 저장

        System.out.println("list = " + list);
        System.out.println("sortedList = " + sortedList);

        String[] words = {"a", "bc", "def"};

        Stream<String> stream = Arrays.stream(words);
        stream.forEach(System.out::println);
//        long count = stream.count(); // 재사용 불가
//        System.out.println("count = " + count);
    }
}
