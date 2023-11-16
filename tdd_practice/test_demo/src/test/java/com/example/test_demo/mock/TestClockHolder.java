package com.example.test_demo.mock;

import com.example.test_demo.common.service.port.ClockHolder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestClockHolder implements ClockHolder {

    private final long millis;

    @Override
    public long millis() {
        return this.millis;
    }
}
