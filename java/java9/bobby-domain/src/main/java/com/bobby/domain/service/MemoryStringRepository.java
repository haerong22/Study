package com.bobby.domain.service;

import java.util.ArrayList;
import java.util.List;

public class MemoryStringRepository implements StringRepository {

    private final List<String> strings = new ArrayList<>();

    @Override
    public void save(String str) {
        strings.add(str);
        System.out.println("메모리에 저장");
    }
}
