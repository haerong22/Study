package collection;

import java.util.ArrayList;
import java.util.List;

public class CopyOf {

    public static void main(String[] args) {
        List<Integer> oldNums = new ArrayList<>();
        oldNums.add(1);
        oldNums.add(2);

        List<Integer> newNums = List.copyOf(oldNums);
        oldNums.add(3);

        oldNums.forEach(o -> System.out.println("old : " + o));
        newNums.forEach(n -> System.out.println("new : " + n));
    }
}
