package lambda.lambda4;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Predicate
 * - 입력 값을 받아 true/false 로 결과를 판단
 * - 조건을 검사하거나 필터링
 * -> 의미의 명확성
 * -> 가독성 및 유지보수성
 */
public class PredicateMain {

    public static void main(String[] args) {

        Predicate<Integer> predicate1 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer v) {
                return v % 2 == 0;
            }
        };
        System.out.println("predicate1.test(10) = " + predicate1.test(10));

        Predicate<Integer> predicate2 = v -> v % 2 == 0;
        System.out.println("predicate2.test(10) = " + predicate2.test(10));

        Function<Integer, Boolean> function = v -> v % 2 == 0;
        System.out.println("function.apply(10) = " + function.apply(10));
    }

    /*
        predicate1.test(10) = true
        predicate2.test(10) = true
        function.apply(10) = true
     */
}
