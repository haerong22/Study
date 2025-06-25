package functional;

import java.util.function.Function;

public class CompositionMain2 {

    public static void main(String[] args) {
        // 1. String -> Integer
        Function<String, Integer> parseInt = Integer::parseInt;

        // 2. Integer -> Integer
        Function<Integer, Integer> square = x -> x * x;

        // 3. Integer -> String
        Function<Integer, String> toString = x -> "결과: " + x;

        // parseInt -> square -> toString
        Function<String, String> func1 = parseInt
                .andThen(square)
                .andThen(toString);

        String result1 = func1.apply("5");
        System.out.println("result1 = " + result1);

        Function<String, Integer> func2 = parseInt.andThen(square);
        Integer result2 = func2.apply("5");
        System.out.println("result2 = " + result2);
    }
    /*
        result1 = 결과: 25
        result2 = 25
     */
}
