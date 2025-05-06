package lambda.lambda5.mystream;

import java.util.List;

public class MyStreamV2Main {

    public static void main(String[] args) {
        // 짝수, 남은 값 2배
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Integer> result = MyStreamV2.of(numbers)
                .filter(n -> n % 2 == 0)
                .map(n -> n * 2)
                .toList();

        System.out.println("result = " + result);
    }
    /*
        result = [4, 8, 12, 16, 20]
     */
}
