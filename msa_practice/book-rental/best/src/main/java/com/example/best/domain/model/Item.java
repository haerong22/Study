package com.example.best.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Item {

    private final Integer no;
    private final String title;

    public static Item create(int no, String title) {
        return new Item(no, title);
    }
}
