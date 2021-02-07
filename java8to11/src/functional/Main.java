package functional;

import java.util.function.*;

public class Main {

    public static void main(String[] args) {

        /**
         * Function<T, R> : T 타입 입력, R 타입 출력
         * BiFunction<T, U, R> : T, U 타입 입력, R 타입 출력
         * Consumer<T> : T 타입 입력, 리턴 X
         * Supplier<T> : 입력 X, 리턴타입 T
         * Predicate<T> : T 타입 입력, Boolean 리턴
         * UnaryOperator<T> : T 타입 입력, T 타입 출력
         * BinaryOperator<T> : T 타입 2개 입력, T 타입 리턴
         */

        BinaryOperator<Integer> aPlusB = (i, j) -> i + j;
        System.out.println(aPlusB.apply(1, 2));

//        UnaryOperator<Integer> plus100 = (i) -> i+100;
//        System.out.println(plus100.apply(1));

//        Predicate<String> startWithHello = (s) -> s.startsWith("Hello");
//        Predicate<Integer> isOdd = (i) -> i % 2 != 0;
//        System.out.println(startWithHello.test("Hello"));
//        System.out.println(startWithHello.test("hi"));
//        System.out.println(isOdd.test(10));

//        Supplier<Integer> get10 = () -> 10;
//        System.out.println(get10.get());
//
//        Consumer<String> print = System.out::println;
//        print.accept("Hello World");

//        Plus10 plus10 = new Plus10();
//
//        Function<Integer, Integer> plus10Lambda = (i) -> i + 10;
//        Function<Integer, Integer> multiply2 = (i) -> i * 2;
//        System.out.println(plus10.apply(1));
//        System.out.println(plus10Lambda.apply(1));
//
//        // 두 함수 조합하는 방법
//        Function<Integer, Integer> multiply2AndPlus10 = plus10Lambda.compose(multiply2); // multiply2 결과값을 plus10Lambda 에 전달
//        System.out.println(multiply2AndPlus10.apply(1));
//        System.out.println(plus10.andThen(multiply2).apply(2));

//        // 함수형 인터페이스를 사용하는 법
//
//        // 1. 익명 내부 클래스 anonymous inner class
//        RunSomething anonymous = new RunSomething() {
//            @Override
//            public int doIt(int num) {
//                return num + 10;
//            }
//        };
//
//        // 2. lambda 표현식
//        RunSomething lambda = (num) -> num + 10; // 한줄
//        RunSomething lambda2 = (num) -> {
//            System.out.println("Hello"); // 여러줄
//            return num + 10;
//        };
//
//        System.out.println(anonymous.doIt(1));
//        System.out.println(lambda.doIt(1));
//        System.out.println(lambda2.doIt(1));
    }
}
