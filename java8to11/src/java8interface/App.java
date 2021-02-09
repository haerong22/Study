package java8interface;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;

/**
 * 자바8 api 에 추가된 기본 메소드들
 *
 * Iterable 의 forEach(), spliterator()
 * Collection 의 stream(), parallelStream(), removeIf(Predicate), spliterator()
 * Comparator 의 reversed(), thenComparing(), static reverseOrder() / naturalOrder()
 *              static nullsFirst() / nullsLast(), static comparing()
 */

public class App {

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("kim");
        names.add("lee");
        names.add("park");
        names.add("choi");



//        Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;
//        names.sort(compareToIgnoreCase.reversed());
//        names.forEach(System.out::println);

//        names.removeIf(s -> s.startsWith("k"));
//        names.forEach(System.out::println);

//        long k = names.stream().map(String::toUpperCase)
//                .filter(s -> s.startsWith("K"))
//                .count();
//        System.out.println(k);

//        names.forEach(System.out::println);
//        Spliterator<String> spliterator = names.spliterator();
//        Spliterator<String> spliterator1 = spliterator.trySplit();
//
//        while (spliterator.tryAdvance(System.out::println));
//        System.out.println("==============================");
//        while (spliterator1.tryAdvance(System.out::println));

//        Foo foo = new Default("kim");
//
//        foo.printName();
//        foo.printNameUpperCase();
//
//        Foo.printHello();
    }
}
