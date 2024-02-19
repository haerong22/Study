package org.example.calculator;

import org.example.calculator.operator.*;

import java.util.List;

public class Calculator {

    private static final List<NewArithmeticOperator> arithmeticOperators =
            List.of(new AdditionOperator(), new SubtractionOperator(), new MultiplicationOperator(), new DivisionOperator());
    public static int calculate(PositiveNumber o1, String operator, PositiveNumber o2) {
        return arithmeticOperators.stream()
                .filter(arithmeticOperators -> arithmeticOperators.supports(operator))
                .map(arithmeticOperators -> arithmeticOperators.calculator(o1, o2))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 연산자가 아닙니다."));
    }
}
