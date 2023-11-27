package com.example.book.application.service;

import com.example.book.IntegrationTestSupport;
import com.example.book.adapter.out.jpa.entity.BookJpaEntity;
import com.example.book.adapter.out.jpa.repository.BookJpaRepository;
import com.example.book.domain.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.time.LocalDate;

import static com.example.book.domain.model.vo.BookStatus.ENTERED;
import static com.example.book.domain.model.vo.Classification.COMPUTER;
import static com.example.book.domain.model.vo.Location.PANGYO;
import static com.example.book.domain.model.vo.Source.SUPPLY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InquiryServiceTest extends IntegrationTestSupport {

    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private BookJpaRepository bookJpaRepository;

    @Test
    @DisplayName("도서를 조회 할 수 있다.")
    void getBookInfo() {
        // given
        BookJpaEntity bookJpaEntity = createTestBookJpaEntity();
        BookJpaEntity saved = bookJpaRepository.save(bookJpaEntity);

        // when
        Book result = inquiryService.getBookInfo(saved.getNo());

        // then
        assertThat(result).isNotNull()
                .extracting(
                        "no", "title", "desc.description", "desc.author", "desc.isbn",
                        "desc.publicationDate", "desc.source", "classification", "bookStatus", "location"
                )
                .containsExactly(
                        saved.getNo(), "test", "test", "test", "test", LocalDate.of(2023, 1, 1),
                        SUPPLY, COMPUTER, ENTERED, PANGYO
                );
    }

    @Test
    @DisplayName("도서가 없으면 에러를 응답한다.")
    void getBookInfoNotExist() {
        // given

        // when

        // then
        assertThatThrownBy(() -> inquiryService.getBookInfo(1))
                .isInstanceOf(InvalidDataAccessApiUsageException.class);
    }

    private BookJpaEntity createTestBookJpaEntity() {
        return BookJpaEntity.builder()
                .title("test")
                .description("test")
                .author("test")
                .isbn("test")
                .publicationDate(LocalDate.of(2023, 1, 1))
                .source(SUPPLY)
                .classification(COMPUTER)
                .bookStatus(ENTERED)
                .location(PANGYO)
                .build();
    }
}