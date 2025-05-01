package lambda.lambda4;

import java.util.function.IntFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;

public class PrimitiveFunction {

    public static void main(String[] args) {

        // 기본형 매개변수 - IntFunction, LongFunction, DoubleFunction
        IntFunction<String> function = x -> "숫자: " + x;
        System.out.println("function.apply(100) = " + function.apply(100));

        // 기본형 반환 - ToIntFunction, ToLongFunction, ToDoubleFunction
        ToIntFunction<String> toIntFunction = s -> s.length();
        System.out.println("toIntFunction = " + toIntFunction.applyAsInt("hello"));

        // 기본형 매개변수, 기본형 반환
        IntToLongFunction intToLongFunction = x -> x * 100L;
        System.out.println("intToLongFunction = " + intToLongFunction.applyAsLong(10));

        // IntUnaryOperator: int -> int
        IntUnaryOperator intUnaryOperator = x -> x * 100;
        System.out.println("intUnaryOperator = " + intUnaryOperator.applyAsInt(10));

        // 기타 - IntConsumer, IntSupplier, IntPredicate
    }
    /*
        function.apply(100) = 숫자: 100
        toIntFunction = 5
        intToLongFunction = 1000
        intUnaryOperator = 1000
     */
}
