package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class CalculationRequestReaderTest {

    @Test
    void 사용자_입력_데이터_읽기() {
        System.setIn(new ByteArrayInputStream("2 + 3".getBytes()));

        CalculationRequest result = CalculationRequestReader.read();

        assertEquals(2, result.getNum1());
        assertEquals("+", result.getOperator());
        assertEquals(3, result.getNum2());
    }

}