package lambda.start;

public class Ex0RefMain {

    /**
     * 변하는 부분과 변하지 않는 부분을 분리
     * 값 매개변수화(Value Parameterization)
     */
    public static void hello(String str) {
        System.out.println("프로그램 시작");
        System.out.println(str);
        System.out.println("프로그램 종료");
    }

    public static void main(String[] args) {
        hello("hello java");
        hello("hello Spring");
    }
}
