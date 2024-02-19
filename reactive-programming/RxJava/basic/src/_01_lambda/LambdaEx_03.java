package _01_lambda;

public class LambdaEx_03 {

    @FunctionalInterface
    interface MyFunction1 {
        int calc(int a);
    }

    @FunctionalInterface
    interface MyFunction2 {
        int calc(int a, int b);
    }

    @FunctionalInterface
    interface MyFunction3 {
        void print();
    }

    public static void main(String[] args) {

        // 파라미터가 1개인 람다식
        MyFunction1 result1 = (x) -> { return x + x; };
        MyFunction1 result2 = x -> x + x; // 괄호 생략 가능
        System.out.println("result1 = " + result1.calc(5));
        System.out.println("result2 = " + result2.calc(5));

        // 파라미터가 2개인 람다식
        MyFunction2 add = (x, y) -> x + y;
        MyFunction2 minus = (x, y) -> x - y;
        System.out.println("add = " + add.calc(1, 2));
        System.out.println("minus = " + minus.calc(1, 2));

        // 파라미터가 없는 람다식
        MyFunction3 myFunction3 = () -> System.out.println("print method");
        myFunction3.print();

        // 함수형 인터페이스 파라미터로 전달
        printSum(3, 4, (x, y) -> x * y);

    }

    static void printSum(int x, int y, MyFunction2 f) {
        System.out.println(f.calc(x, y));
    }
}
