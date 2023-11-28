package com.example.best.persistence;

import com.example.best.domain.model.BestBook;
import com.example.best.domain.model.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@DataMongoTest
class BestBookRepositoryTest {

    @Autowired
    private BestBookRepository bestBookRepository;

    @AfterEach
    void clear() {
        bestBookRepository.deleteAll();
    }

    @Test
    @DisplayName("베스트 도서를 저장한다.")
    void save() {
        // given
        String id = UUID.randomUUID().toString();
        Item item = Item.create(1, "SpringBoot");
        long count = 1;

        BestBook bestBook = BestBook.builder()
                .id(id)
                .item(item)
                .rentCount(count)
                .build();

        // when
        bestBookRepository.save(bestBook);

        // then
        BestBook result = bestBookRepository.findById(id).get();

        assertThat(result).isNotNull()
                .extracting("item.no", "item.title", "rentCount")
                .containsExactly(1, "SpringBoot", 1L);
    }

    @Test
    @DisplayName("Item으로 베스트 도서를 조회한다.")
    void findBestBookByItem() {
        // given
        String id = UUID.randomUUID().toString();
        Item item = Item.create(1, "SpringBoot");
        long count = 1;

        BestBook bestBook = BestBook.builder()
                .id(id)
                .item(item)
                .rentCount(count)
                .build();

        bestBookRepository.save(bestBook);

        // when
        BestBook result = bestBookRepository.findBestBookByItem(item).get();

        // then
        assertThat(result).isNotNull()
                .extracting("item.no", "item.title", "rentCount")
                .containsExactly(1, "SpringBoot", 1L);
    }

    @Test
    @DisplayName("Id로 베스트 도서를 조회한다.")
    void findBestBookById() {
        // given
        String id = UUID.randomUUID().toString();
        Item item = Item.create(1, "SpringBoot");
        long count = 1;

        BestBook bestBook = BestBook.builder()
                .id(id)
                .item(item)
                .rentCount(count)
                .build();

        bestBookRepository.save(bestBook);

        // when
        BestBook result = bestBookRepository.findById(id).get();

        // then
        assertThat(result).isNotNull()
                .extracting("item.no", "item.title", "rentCount")
                .containsExactly(1, "SpringBoot", 1L);
    }

    @Test
    @DisplayName("베스트 도서리스트를 조회한다.")
    void findAll() {
        // given
        String id = UUID.randomUUID().toString();
        Item item = Item.create(1, "SpringBoot");
        long count = 1;

        BestBook bestBook = BestBook.builder().id(id).item(item).rentCount(count).build();

        String id2 = UUID.randomUUID().toString();
        Item item2 = Item.create(2, "Java");
        long count2 = 10;

        BestBook bestBook2 = BestBook.builder().id(id2).item(item2).rentCount(count2).build();

        bestBookRepository.saveAll(List.of(bestBook, bestBook2));

        // when
        List<BestBook> result = bestBookRepository.findAll();

        // then
        assertThat(result).hasSize(2)
                .extracting("item.no", "item.title", "rentCount")
                .containsExactlyInAnyOrder(
                        tuple(1, "SpringBoot", 1L),
                        tuple(2, "Java", 10L)
                );
    }

}