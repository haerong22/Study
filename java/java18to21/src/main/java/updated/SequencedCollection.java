package updated;

import java.util.List;

public class SequencedCollection {

    public static void main(String[] args) {

        List<Integer> numbers = List.of(1, 2, 3, 4);
        System.out.println(numbers.getLast()); // 4

        List<Integer> reversed = numbers.reversed();

        System.out.println("numbers = " + numbers);
    }
}
