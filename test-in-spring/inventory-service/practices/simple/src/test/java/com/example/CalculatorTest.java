package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CalculatorTest {
    @Mock
    Calculator calculator;

    @DisplayName("a와 b를 더했을 때, 그 결과는 3이다.")
    @Test
    void addOneAndTwoThenCheckItIsThree() {
        // given
        Integer a = 1;
        Integer b = 2;
        given(calculator.add(anyInt(), anyInt()))
                .willAnswer(invocation -> {
                    Integer first = invocation.getArgument(0);
                    Integer second = invocation.getArgument(1);

                    return first + second;
                });

        // when
        Integer c = calculator.add(a, b);

        // then
        Integer expected = a + b;
        assertEquals(expected, c);
    }
}