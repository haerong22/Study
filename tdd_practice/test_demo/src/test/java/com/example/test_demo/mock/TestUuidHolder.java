package com.example.test_demo.mock;

import com.example.test_demo.common.service.port.UuidHolder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestUuidHolder implements UuidHolder {

    private final String uuid;

    @Override
    public String random() {
        return this.uuid;
    }
}
