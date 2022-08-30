package org.example.calculator.domain;

import java.util.Arrays;

public enum EnumArithmeticOperator {
    ADDITION("+") {
        @Override
        public int calculate(final int operand1, final int operand2) {
            return operand1 + operand2;
        }
    },
    SUBTRACTION("-") {
        @Override
        public int calculate(final int operand1, final int operand2) {
            return operand1 - operand2;
        }
    },
    MULTIPLICATION("*") {
        @Override
        public int calculate(final int operand1, final int operand2) {
            return operand1 * operand2;
        }
    },
    DIVISION("/") {
        @Override
        public int calculate(final int operand1, final int operand2) {
            if (operand2 == 0) {
                throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
            }
            return operand1 / operand2;
        }
    };

    private final String operator;

    EnumArithmeticOperator(String operator) {
        this.operator = operator;
    }

    public abstract int calculate(final int operand1, final int operand2);

    public static int calculate(final int operand1, final String operator, final int operand2) {
        EnumArithmeticOperator selectedEnumArithmeticOperator = Arrays.stream(values())
                .filter(value -> value.operator.equals(operator))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 사칙연산이 아닙니다."));

        return selectedEnumArithmeticOperator.calculate(operand1, operand2);
    }
}