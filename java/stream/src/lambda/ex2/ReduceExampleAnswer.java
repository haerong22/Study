package lambda.ex2;

import lambda.MyReducer;

import java.util.List;

public class ReduceExampleAnswer {

    public static int reduce(List<Integer> list, int initial, MyReducer reducer) {
        int result = initial;
        for (int val : list) {
            result = reducer.reduce(result, val);
        }

        return result;
    }

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4);
        System.out.println("리스트: " + numbers);

        // 합 구하기 (초기값 0)
        int sum = reduce(numbers, 0, (a, b) -> a + b);
        System.out.println("합 = " + sum);

        // 곱 구하기 (초기값 1)
        int multiple = reduce(numbers, 1, (a, b) -> a * b);
        System.out.println("곱 = " + multiple);
    }

    /*
        리스트: [1, 2, 3, 4]
        합 = 10
        곱 = 24
     */
}
