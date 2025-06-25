package functional;

import java.util.ArrayList;
import java.util.List;

public class DeclarativeMain {

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // 명령형
        List<Integer> result1 = new ArrayList<>();
        for (Integer number : numbers) {
            if (number % 2 == 0) {
                result1.add(number * number);
            }
        }
        System.out.println("Imperative Result: " + result1);

        // 선언형
        List<Integer> result2 = numbers.stream()
                .filter(number -> number % 2 == 0)
                .map(number -> number * number)
                .toList();
        System.out.println("Declarative Result: " + result2) ;
    }
    /*
        Imperative Result: [4, 16, 36, 64, 100]
        Declarative Result: [4, 16, 36, 64, 100]
     */
}
