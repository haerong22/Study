package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {

    @Test
    void 덧셈_연산() {
        long num1 = 2;
        String operator = "+";
        long num2 = 3;

        long result = Calculator.calulate(num1, operator, num2);

        assertEquals(5, result);
    }

    @Test
    void 뺄셈_연산() {
        long num1 = 2;
        String operator = "-";
        long num2 = 3;

        long result = Calculator.calulate(num1, operator, num2);

        assertEquals(-1, result);
    }

    @Test
    void 곱셈_연산() {
        long num1 = 2;
        String operator = "*";
        long num2 = 3;

        long result = Calculator.calulate(num1, operator, num2);

        assertEquals(6, result);
    }

    @Test
    void 나눗셈_연산() {
        long num1 = 6;
        String operator = "/";
        long num2 = 3;

        long result = Calculator.calulate(num1, operator, num2);

        assertEquals(2, result);
    }

    @Test
    void 잘못된_연산자_요청시_에러() {
        long num1 = 6;
        String operator = "x";
        long num2 = 3;

        assertThrows(InvalidOperatorException.class, () -> Calculator.calulate(num1, operator, num2));
    }

}