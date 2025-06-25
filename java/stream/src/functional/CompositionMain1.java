package functional;

import java.util.function.Function;

public class CompositionMain1 {

    public static void main(String[] args) {
        Function<Integer, Integer> square = x -> x * x;
        Function<Integer, Integer> add =  x -> x + 1;

        // 함수 합성
        Function<Integer, Integer> newFunc1 = square.compose(add); // square(add(2)) -> square(3) -> 9
        System.out.println("newFunc1.apply(2) = " + newFunc1.apply(2));

        Function<Integer, Integer> newFunc2 = square.andThen(add); // add(square(2)) -> add(4) -> 5
        System.out.println("newFunc2.apply(2) = " + newFunc2.apply(2));
    }
    /*
        newFunc1.apply(2) = 9
        newFunc2.apply(2) = 5
     */
}
