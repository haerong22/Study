package org.example.calculator.domain;

import org.example.calculator.tobe.*;

import java.util.List;

public class Calculator {

    private static final List<ArithmeticOperator> arithmeticOperators = List.of(new AdditionOperator(), new DivisionOperator(), new SubtractionOperator(), new MultiplicationOperator());
    /**
     * 간단한 사칙연산을 할 수 있다.
     * 양수로만 계산할 수 있다.
     * 나눗셈에서 0을 나누는 경우 IllegalArgument 예외를 발생시킨다. MVC패턴(Model-View-Controller) 기반으로 구현한다
     */
    public static int calculate(PositiveNumber positiveOperand1, String operator, PositiveNumber positiveOperand2) {
        return arithmeticOperators.stream()
                .filter(arithmeticOperator -> arithmeticOperator.supports(operator))
                .map(arithmeticOperator -> arithmeticOperator.calculate(positiveOperand1, positiveOperand2))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 사칙연산이 아닙니다."));
    }
}