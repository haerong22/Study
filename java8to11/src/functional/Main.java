package functional;

public class Main {

    public static void main(String[] args) {

        // 함수형 인터페이스를 사용하는 법

        // 1. 익명 내부 클래스 anonymous inner class
        RunSomething anonymous = new RunSomething() {
            @Override
            public int doIt(int num) {
                return num + 10;
            }
        };

        // 2. lambda 표현식
        RunSomething lambda = (num) -> num + 10; // 한줄
        RunSomething lambda2 = (num) -> {
            System.out.println("Hello"); // 여러줄
            return num + 10;
        };

        System.out.println(anonymous.doIt(1));
        System.out.println(lambda.doIt(1));
        System.out.println(lambda2.doIt(1));
    }
}
