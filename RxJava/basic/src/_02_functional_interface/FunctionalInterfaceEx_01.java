package _02_functional_interface;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;

/**
 * Function  : T -> R
 * Consumer  : T -> void
 * Supplier  : void -> T
 * Operator  : T -> T
 * Predicate : T -> boolean
 *
 */
public class FunctionalInterfaceEx_01 {

    public static void main(String[] args) {

        // 예외 throw 불가능 -> try catch 구문을 이용하여 예외처리를 한다.
        Function<BufferedReader, String> f = bufferedReader -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        // Predicate 사용
        List<String> list = Arrays.asList("", "test1", "test2", "test3", "");
        List<String> result = filter(list,  s -> !s.isEmpty()); // 리스트의 공백을 제거하는 predicate 전달

        System.out.println("list = " + list);
        System.out.println("result = " + result);

        // Consumer 사용
        List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5);
        forEach(list2, i -> System.out.println(i)); // 리스트 값 출력하는 consumer 전달

        // Function 사용
        List<String> list3 = Arrays.asList("test1", "test22", "test333");
        List<Integer> result3 = map(list3, s -> s.length()); // 각 요소의 길이 출력
        System.out.println("result3 = " + result3);

        // Operator 사용
        UnaryOperator<String> u = str -> str + " test";
        String result4 = u.apply("operator");
        System.out.println("result4 = " + result4);

        // Supplier 사용
        Supplier<String> s = () -> "supplier";
        String result5 = s.get();
        System.out.println("result5 = " + result5);
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(f.apply(t));
        }
        return result;
    }

    public static <T> void forEach(List<T> list, Consumer<T> c) {
        for (T t : list) {
            c.accept(t);
        }
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        final List<T> result = new ArrayList<>();

        for (T t : list) {
            if (p.test(t)) {
                result.add(t);
            }
        }
        return result;
    }
}
