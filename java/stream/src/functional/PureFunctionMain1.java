package functional;

import java.util.function.Function;

public class PureFunctionMain1 {

    public static void main(String[] args) {
        Function<Integer, Integer> func = x -> x * 2;
        int result1 = func.apply(5);
        int result2 = func.apply(5);
        System.out.println("result1 == result2 = " + (result1 == result2));
    }
    /*
        result1 == result2 = true
     */
}
