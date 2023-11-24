package com.example.rental.domain.model.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Item {

    private final Long no;
    private final String title;

    @Builder
    private Item(Long no, String title) {
        this.no = no;
        this.title = title;
    }

    public static Item create(Long no, String title) {
        return new Item(no, title);
    }
}
