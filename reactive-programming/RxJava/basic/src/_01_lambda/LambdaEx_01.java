package _01_lambda;

public class LambdaEx_01 {

    public static void main(String[] args) {

        ExInterface.print("interface static method test");

        ExInterface exInterface = () -> System.out.println("implement abstract method");

        exInterface.getMsg();

        String result = exInterface.defaultMethod();
        System.out.println("result = " + result);

    }
}
