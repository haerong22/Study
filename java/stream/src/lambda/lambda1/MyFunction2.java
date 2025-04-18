package lambda.lambda1;

import lambda.MyFunction;

public class MyFunction2 {

    public static void main(String[] args) {
        MyFunction myFunction = (int a, int b) -> {
            return a + b;
        };

        int result = myFunction.apply(1, 2);
        System.out.println("result = " + result);
    }
}
