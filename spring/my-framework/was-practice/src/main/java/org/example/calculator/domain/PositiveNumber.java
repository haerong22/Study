package org.example.calculator.domain;

public class PositiveNumber {
    public static final String ZERO_OR_NEGATIVE_NUMBER_EXCEPTION_MESSAGE = "0 또는 음수를 전달할 수 없습니다.";
    private final int value;

    public PositiveNumber(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (isZeroOrNegativeNumber(value)) {
            throw new IllegalArgumentException(ZERO_OR_NEGATIVE_NUMBER_EXCEPTION_MESSAGE);
        }
    }

    private boolean isZeroOrNegativeNumber(int value) {
        return value <= 0;
    }

    public int toInt() {
        return value;
    }
}