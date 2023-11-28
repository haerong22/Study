package com.example.best.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BestBook {

    @Id
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

    public void update(BestBook book) {
        this.item = book.getItem();
        this.rentCount = book.rentCount;
    }

    public Long increase() {
        return ++this.rentCount;
    }
}
