package _03_stream;

import java.util.Arrays;
import java.util.List;

public class StreamEx_09 {

    public static void main(String[] args) {

        // map -> 다른 타입으로 변환
        List<Integer> result = Dish.menu.stream()
                .map(Dish::getName)// Dish -> String
                .map(String::length)// String -> Integer
                .toList();

        System.out.println("result = " + result);

        // flatMap
        List<String> words = Arrays.asList("Hello", "world");

        List<String> uniqueCharacter = words.stream()
                .map(word -> word.split("")) // String -> String[] 이 되므로 Stream<String[]>
                .flatMap(Arrays::stream) // Stream<String>
                .distinct()
                .toList();

        System.out.println("uniqueCharacter = " + uniqueCharacter);
    }
}
