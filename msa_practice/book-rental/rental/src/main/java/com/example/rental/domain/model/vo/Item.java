package com.example.rental.domain.model.vo;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(no, item.no) && Objects.equals(title, item.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(no, title);
    }
}
