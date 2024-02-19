package org.example.calculator.operator;

public interface NewArithmeticOperator {

    boolean supports(String operator);
    int calculator(PositiveNumber operand1, PositiveNumber operand2);
}
