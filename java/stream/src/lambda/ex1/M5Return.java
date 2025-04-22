package lambda.ex1;

import lambda.MyFunction;

public class M5Return {

    public static void main(String[] args) {
        MyFunction add = getOperator("add");
        System.out.println("add.apply(1, 2) = " + add.apply(1, 2));

        MyFunction sub = getOperator("sub");
        System.out.println("sub.apply(1, 2) = " + sub.apply(1, 2));

        MyFunction xxx = getOperator("xxx");
        System.out.println("xxx.apply(1, 2) = " + xxx.apply(1, 2));
    }
    
    public static MyFunction getOperator(String operator) {
        return switch (operator) {
            case "add" -> (a, b) -> a + b;
            case "sub" -> (a, b) -> a - b;
            default -> (a, b) -> 0;
        };
    }
}
