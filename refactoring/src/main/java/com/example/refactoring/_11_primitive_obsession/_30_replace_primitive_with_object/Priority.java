package com.example.refactoring._11_primitive_obsession._30_replace_primitive_with_object;

import java.util.List;

public class Priority {

    private final String value;

    private final List<String> type = List.of("low", "normal", "high", "rush");

    public Priority(String value) {
        if (type.contains(value)) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("illegal value for priority : " + value);
        }
    }

    @Override
    public String toString() {
        return this.value;
    }

    private int index() {
        return this.type.indexOf(this.value);
    }

    public boolean higherThan(Priority other) {
        return this.index() > other.index();
    }
}
