package lambda.lambda3;

import java.util.function.Function;

public class TargetType3 {

    public static void main(String[] args) {

        // O
        Function<Integer, String> functionA = i -> "value = " + i;
        System.out.println("functionA.apply(10) = " + functionA.apply(10));

        // O
        Function<Integer, String> functionB = functionA;
        System.out.println("functionB.apply(10) = " + functionB.apply(10));
    }
}
