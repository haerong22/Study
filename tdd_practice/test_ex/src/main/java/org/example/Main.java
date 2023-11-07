package org.example;

public class Main {
    public static void main(String[] args) {
        CalculationRequest parts = CalculationRequestReader.read();

        long answer = Calculator.calulate(parts.getNum1(), parts.getOperator(), parts.getNum2());

        System.out.println(answer);
    }
}