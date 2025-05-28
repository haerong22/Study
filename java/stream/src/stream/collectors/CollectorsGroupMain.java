package stream.collectors;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectorsGroupMain {

    public static void main(String[] args) {

        // 첫 글자 알파벳을 기준으로 그룹화
        List<String> names = List.of("Apple", "Avocado", "Banana", "Blueberry", "Cherry");
        Map<String, List<String>> grouped = names.stream()
                .collect(Collectors.groupingBy(name -> name.substring(0, 1)));
        System.out.println("grouped = " + grouped);

        // 짝수 인지 여부로 분할
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
        Map<Boolean, List<Integer>> partitioned = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("partitioned = " + partitioned);
    }
    /*
        grouped = {A=[Apple, Avocado], B=[Banana, Blueberry], C=[Cherry]}
        partitioned = {false=[1, 3, 5], true=[2, 4, 6]}
     */
}
