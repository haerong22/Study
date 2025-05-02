package lambda.ex3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class MapExampleAnswer {

    public static List<String> map(List<String> list, UnaryOperator<String> func) {
        List<String> result = new ArrayList<>();
        for (String str : list) {
            result.add(func.apply(str));
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> words = List.of("hello", "java", "lambda");
        System.out.println("원본 리스트: " + words);

        // 대문자 변환
        List<String> upperCases = map(words, s -> s.toUpperCase());
        System.out.println("upperCases = " + upperCases);

        // 앞뒤에 *** 붙이기
        List<String> stars = map(words, s -> "***" + s + "***");
        System.out.println("stars = " + stars);
    }

    /*
        원본 리스트: [hello, java, lambda]
        upperCases = [HELLO, JAVA, LAMBDA]
        stars = [***hello***, ***java***, ***lambda***]
     */
}
