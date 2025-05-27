package stream.collectors;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorsMapMain {

    public static void main(String[] args) {

        // Map
        Map<String, Integer> map1 = Stream.of("Apple", "Banana", "Tomato")
                .collect(Collectors.toMap(
                        name -> name, // key
                        name -> name.length()  // value
                ));
        System.out.println("map1 = " + map1);

        // 키 중복 시 예외 발생
//        Map<String, Integer> map2 = Stream.of("Apple", "Apple", "Tomato")
//                .collect(Collectors.toMap(
//                        name -> name, // key
//                        name -> name.length()  // value
//                ));
//        System.out.println("map2 = " + map2);

        // 키 중복 대안(병합)
        Map<String, Integer> map3 = Stream.of("Apple", "Apple", "Tomato")
                .collect(Collectors.toMap(
                        name -> name, // key
                        name -> name.length(),  // value
                        (oldVal, newVal) -> oldVal + newVal // 중복될 경우 처리
                ));
        System.out.println("map3 = " + map3);

        // Map 타입 지정
        Map<String, Integer> map4 = Stream.of("Apple", "Apple", "Tomato")
                .collect(Collectors.toMap(
                        name -> name, // key
                        name -> name.length(),  // value
                        (oldVal, newVal) -> oldVal + newVal, // 중복될 경우 처리
                        LinkedHashMap::new // 타입 지정
                ));
        System.out.println("map4 = " + map4.getClass());
    }

    /*
        map1 = {Apple=5, Tomato=6, Banana=6}
        map3 = {Apple=10, Tomato=6}
        map4 = class java.util.LinkedHashMap
     */
}
