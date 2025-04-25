package lambda.lambda3;

public class GenericMain4 {

    public static void main(String[] args) {
        GenericFunction<String, String> toUpperCase = s -> s.toUpperCase();
        GenericFunction<String, Integer> stringLength = s -> s.length();
        GenericFunction<Integer, Integer> square = i -> i * i;
        GenericFunction<Integer, Boolean> isEven = i -> i % 2 == 0;

        System.out.println(toUpperCase.apply("hello world"));
        System.out.println(stringLength.apply("hello world"));
        System.out.println(square.apply(3));
        System.out.println(isEven.apply(2));
    }

    /*
        HELLO WORLD
        11
        9
        true
     */

    @FunctionalInterface
    interface GenericFunction<T, R> {
        R apply(T s);
    }
}
