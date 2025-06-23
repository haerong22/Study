package functional;

import java.util.function.Function;

public class SideEffectMain1 {

    public static int count = 0;

    public static void main(String[] args) {
        System.out.println("before count = " + count);

        Function<Integer, Integer> func = x -> {
            count++;
            return x * 2;
        };
        func.apply(10);
        System.out.println("after count = " + count);
    }
    /*
        before count = 0
        after count = 1
     */
}
