package com.example.best.domain.service;

import com.example.best.IntegrationTestSupport;
import com.example.best.domain.model.BestBook;
import com.example.best.domain.model.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

class BestBookServiceTest extends IntegrationTestSupport {

    @Autowired
    private BestBookService bestBookService;

    @Test
    @DisplayName("전체 베스트 도서 리스트를 조회한다.")
    void getAllBooks() {
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
        List<BestBook> result = bestBookService.getAllBooks();

        // then
        assertThat(result).hasSize(2)
                .extracting("item.no", "item.title", "rentCount")
                .containsExactlyInAnyOrder(
                        tuple(1, "SpringBoot", 1L),
                        tuple(2, "Java", 10L)
                );
    }

    @Test
    @DisplayName("Id로 베스트 도서를 조회한다.")
    void getBookById() {
        // given
        String id = UUID.randomUUID().toString();
        Item item = Item.create(1, "SpringBoot");
        long count = 1;

        BestBook bestBook = BestBook.builder().id(id).item(item).rentCount(count).build();

        bestBookRepository.save(bestBook);

        // when
        BestBook result = bestBookService.getBookById(id).get();

        // then
        assertThat(result).isNotNull()
                .extracting("item.no", "item.title", "rentCount")
                .containsExactly(1, "SpringBoot", 1L);
    }

    @Test
    @DisplayName("베스트 도서 카운트를 증가시킨다.")
    void dealBestBook() {
        // given
        String id = UUID.randomUUID().toString();
        Item item = Item.create(1, "SpringBoot");
        long count = 1;

        BestBook bestBook = BestBook.builder().id(id).item(item).rentCount(count).build();

        bestBookRepository.save(bestBook);

        // when
        bestBookService.dealBestBook(item);

        // then
        BestBook result = bestBookRepository.findById(id).get();

        assertThat(result).isNotNull()
                .extracting("item.no", "item.title", "rentCount")
                .containsExactly(1, "SpringBoot", 2L);
    }

    @Test
    @DisplayName("베스트 도서 카운트를 증가시킬때 도서가 없으면 생성 후 증가시킨다.")
    void dealBestBookNoItem() {
        // given
        Item item = Item.create(1, "SpringBoot");

        // when
        bestBookService.dealBestBook(item);

        // then
        BestBook result = bestBookRepository.findBestBookByItem(item).get();

        assertThat(result).isNotNull()
                .extracting("item.no", "item.title", "rentCount")
                .containsExactly(1, "SpringBoot", 1L);
    }

    @Test
    @DisplayName("베스트 도서를 저장한다.")
    void saveBook() {
        // given
        String id = UUID.randomUUID().toString();
        Item item = Item.create(1, "SpringBoot");
        long count = 1;

        BestBook bestBook = BestBook.builder().id(id).item(item).rentCount(count).build();

        // when
        bestBookService.saveBook(bestBook);

        // then
        List<BestBook> result = bestBookRepository.findAll();

        assertThat(result).hasSize(1)
                .extracting("item.no", "item.title", "rentCount")
                .containsExactlyInAnyOrder(
                        tuple(1, "SpringBoot", 1L)
                );
    }

    @Test
    @DisplayName("베스트 도서를 업데이트 한다.")
    void update() {
        // given
        String id = UUID.randomUUID().toString();
        Item item = Item.create(1, "SpringBoot");
        long count = 1;

        BestBook bestBook = BestBook.builder().id(id).item(item).rentCount(count).build();

        bestBookRepository.save(bestBook);

        Item updateItem = Item.create(2, "Java");
        long updateCount = 10;

        BestBook updateBestBook = BestBook.builder().item(updateItem).rentCount(updateCount).build();

        // when
        bestBookService.update(id, updateBestBook);

        // then
        BestBook result = bestBookRepository.findById(id).get();

        assertThat(result).isNotNull()
                .extracting("item.no", "item.title", "rentCount")
                .containsExactly(2, "Java", 10L);
    }

    @Test
    @DisplayName("베스트 도서를 업데이트 할 때 도서가 없으면 null을 리턴한다.")
    void updateNotExist() {
        // given
        String id = UUID.randomUUID().toString();

        Item updateItem = Item.create(2, "Java");
        long updateCount = 10;

        BestBook updateBestBook = BestBook.builder().item(updateItem).rentCount(updateCount).build();

        // when
        BestBook result = bestBookService.update(id, updateBestBook);

        // then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("베스트 도서를 삭제한다.")
    void deleteBook() {
        // given
        String id = UUID.randomUUID().toString();
        Item item = Item.create(1, "SpringBoot");
        long count = 1;

        BestBook bestBook = BestBook.builder().id(id).item(item).rentCount(count).build();

        bestBookRepository.save(bestBook);

        // when
        boolean result = bestBookService.deleteBook(id);

        // then
        Optional<BestBook> best = bestBookRepository.findById(id);

        assertThat(result).isTrue();
        assertThat(best.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("베스트 도서를 삭제 시 도서가 없으면 false를 리턴한다.")
    void deleteBookNotExist() {
        // given
        String id = UUID.randomUUID().toString();

        // when
        boolean result = bestBookService.deleteBook(id);

        // then
        assertThat(result).isFalse();
    }
}