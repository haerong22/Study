package lambda.ex3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FilterExampleAnswer {

    public static List<Integer> filter(List<Integer> list, Predicate<Integer> predicate) {
        List<Integer> result = new ArrayList<>();
        for (int val : list) {
            if (predicate.test(val)) {
                result.add(val);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> numbers = List.of(-3, -2, -1, 1, 2, 3, 5);
        System.out.println("원본 리스트: " + numbers);

        // 음수만
        List<Integer> negatives = filter(numbers, v -> v < 0);
        System.out.println("음수만: " + negatives);

        // 짝수만
        List<Integer> evens = filter(numbers, v -> v % 2 == 0);
        System.out.println("짝수만: " + evens);
    }

    /*
        원본 리스트: [-3, -2, -1, 1, 2, 3, 5]
        음수만: [-3, -2, -1]
        짝수만: [-2, 2]
     */
}
