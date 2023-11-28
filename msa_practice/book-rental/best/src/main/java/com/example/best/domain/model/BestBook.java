package com.example.best.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BestBook {

    private String id;
    private Item item;
    private long rentCount;

    public static BestBook register(Item item) {
        return BestBook.builder()
                .id(UUID.randomUUID().toString())
                .item(item)
                .rentCount(1)
                .build();
    }

    public Long increase() {
        return ++this.rentCount;
    }
}
