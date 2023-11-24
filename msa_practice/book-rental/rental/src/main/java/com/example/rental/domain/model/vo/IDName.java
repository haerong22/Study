package com.example.rental.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IDName {

    private final String id;
    private final String name;

    public static IDName create(String id, String name) {
        return new IDName(id, name);
    }
}
