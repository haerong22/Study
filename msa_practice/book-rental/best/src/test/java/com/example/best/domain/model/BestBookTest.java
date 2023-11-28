package com.example.best.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BestBookTest {

    @Test
    @DisplayName("베스트 도서를 생성할 수 있다.")
    void register() {
        // given
        Item item = Item.create(1, "SpringBoot");

        // when
        BestBook bestBook = BestBook.register(item);

        // then
        assertThat(bestBook).isNotNull()
                .extracting("item.no", "item.title", "rentCount")
                .containsExactly(1, "SpringBoot", 1L);
    }

    @Test
    @DisplayName("대여 횟수를 증가할 수 있다.")
    void increase() {
        // given
        BestBook bestBook = createTestBestBook(Item.create(1, "Springboot"), 10);

        // when
        Long cnt = bestBook.increase();

        // then
        assertThat(cnt).isEqualTo(11L);
    }

    @Test
    @DisplayName("도서를 업데이트 한다.")
    void update() {
        // given
        Item item = Item.create(1, "Springboot");
        Item updateItem = Item.create(2, "Java");

        BestBook bestBook = createTestBestBook(item, 1);
        BestBook updateBestBook = createTestBestBook(updateItem, 50);

        // when
        bestBook.update(updateBestBook);

        // then
        assertThat(bestBook).isNotNull()
                .extracting("item.no", "item.title", "rentCount")
                .containsExactly(2, "Java", 50L);
    }

    private BestBook createTestBestBook(Item item, long count) {
        return BestBook.builder()
                .item(item)
                .rentCount(count)
                .build();
    }

}