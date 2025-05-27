package stream.collectors;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorsBasicMain {

    public static void main(String[] args) {

        // 수정 가능 리스트 반환
        List<String> list = Stream.of("Java", "Spring", "JPA")
                .collect(Collectors.toList());
        System.out.println("list = " + list);

        // 수정 불가 리스트
        List<Integer> unmodifiableList = Stream.of(1, 2, 3)
                .collect(Collectors.toUnmodifiableList());
        System.out.println("unmodifiableList = " + unmodifiableList);

        // Set
        Set<Integer> set = Stream.of(1, 2, 2, 3, 3, 3)
                .collect(Collectors.toSet());
        System.out.println("set = " + set);

        // 타입 지정
        TreeSet<Integer> treeSet = Stream.of(3, 4, 5, 2, 1)
                .collect(Collectors.toCollection(TreeSet::new)); // TreeSet 은 정렬 상태 유지
        System.out.println("treeSet = " + treeSet);
    }

    /*
        list = [Java, Spring, JPA]
        unmodifiableList = [1, 2, 3]
        set = [1, 2, 3]
        treeSet = [1, 2, 3, 4, 5]
     */
}
