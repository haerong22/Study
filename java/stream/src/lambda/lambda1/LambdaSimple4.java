package lambda.lambda1;

public class LambdaSimple4 {

    public static void main(String[] args) {

        // 기본
        MyCall call1 = (int value) -> value * 2;
        System.out.println("call1 = " + call1.call(10));

        // 타입 추론
        MyCall call2 = (value) -> value * 2;
        System.out.println("call2 = " + call2.call(10));

        // 매개변수 1개 인 경우 () 생략 가능
        MyCall call3 = value -> value * 2;
        System.out.println("call3 = " + call3.call(10));

    }

    interface MyCall {
        int call(int value);
    }
}
