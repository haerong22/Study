package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculationRequestTest {

    @Test
    void 유효한_숫자_파싱() {

        String[] parts = {"2", "+", "3"};

        CalculationRequest result = CalculationRequest.of(parts);

        assertEquals(2, result.getNum1());
        assertEquals("+", result.getOperator());
        assertEquals(3, result.getNum2());
    }

    @Test
    void 유효한_세자리_숫자_파싱() {

        String[] parts = {"223", "+", "313"};

        CalculationRequest result = CalculationRequest.of(parts);

        assertEquals(223, result.getNum1());
        assertEquals("+", result.getOperator());
        assertEquals(313, result.getNum2());
    }

    @Test
    void 유효하지_않은_길이의_데이터_입력시_에러() {

        String[] parts = {"223", "+"};

        assertThrows(InvalidRequestException.class, () -> CalculationRequest.of(parts));
    }

    @Test
    void 유효하지_않은_연산자_입력시_에러() {

        String[] parts = {"223", "x", "313"};

        assertThrows(InvalidOperatorException.class, () -> CalculationRequest.of(parts));
    }

    @Test
    void 유효하지_않은_길이의_연산자_입력시_에러() {

        String[] parts = {"223", "+=", "313"};

        assertThrows(InvalidOperatorException.class, () -> CalculationRequest.of(parts));
    }

}