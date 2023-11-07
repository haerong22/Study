package org.example;

import java.util.Arrays;

public class CalculationRequest {

    private long num1;
    private long num2;
    private String operator;

    public static CalculationRequest of(String[] parts) {
        if (parts.length != 3) {
            throw new InvalidRequestException();
        }

        if (parts[1].length() != 1 || isInvalidOperator(parts[1])) {
            throw new InvalidOperatorException();
        }

        CalculationRequest request = new CalculationRequest();
        request.num1 = Long.parseLong(parts[0]);
        request.num2 = Long.parseLong(parts[2]);
        request.operator = parts[1];
        return request;
    }

    private static boolean isInvalidOperator(String operator) {
        return !Arrays.asList("+", "-", "*", "/").contains(operator);
    }

    public long getNum1() {
        return num1;
    }

    public long getNum2() {
        return num2;
    }

    public String getOperator() {
        return operator;
    }
}
