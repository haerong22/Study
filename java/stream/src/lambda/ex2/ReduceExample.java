package lambda.ex2;

import lambda.MyReducer;

import java.util.List;

public class ReduceExample {

    public static int reduce(List<Integer> list, int initial, MyReducer reducer) {
        return 0;
    }

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4);
        System.out.println("리스트: " + numbers);

        // 합 구하기 (초기값 0)

        // 곱 구하기 (초기값 1)
    }
}
