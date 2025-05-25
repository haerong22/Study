package stream.operation;

import java.util.Optional;

public class OptionalSimpleMain {

    public static void main(String[] args) {
        Optional<Integer> optional1 = Optional.of(10);
        System.out.println("optional1 = " + optional1);
        if (optional1.isPresent()) {
            Integer i = optional1.get();
            System.out.println("i = " + i);
        }

        Optional<Object> optional2 = Optional.ofNullable(null);
        System.out.println("optional2 = " + optional2);
        if (optional2.isPresent()) {
            Object o = optional2.get();
            System.out.println("o = " + o);
        }

//        Object o = optional2.get(); // NoSuchElementException
    }
}
