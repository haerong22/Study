package org.example.ex05;

import java.util.List;
import java.util.function.Predicate;

/**
 * 고차함수
 */
public class Exam05 {

    private Predicate<String> stringPredicate = new Predicate<String>() {
        @Override
        public boolean test(String s) {
            return s.equals("a");
        }
    };

    public Exam05() {

        var strList = List.of("a", "b", "c", "d", "e");

        var result = strList.stream()
                .filter(it -> it.equals("a"))
                .toList();
    }

    public static void main(String[] args) {
        new Exam05();
    }
}
