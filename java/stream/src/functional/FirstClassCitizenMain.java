package functional;

import java.util.function.Function;

public class FirstClassCitizenMain {

    public static void main(String[] args) {
        Function<Integer, Integer> func = x -> x * 2;

        // 함수를 전달
        applyFunction(10, func);

        // 함수를 반환
        getFunc().apply(10);
    }

    private static Function<Integer, Integer> getFunc() {
        return x -> x * 2;
    }

    private static Integer applyFunction(int input, Function<Integer, Integer> func) {
        return func.apply(input);
    }
}
