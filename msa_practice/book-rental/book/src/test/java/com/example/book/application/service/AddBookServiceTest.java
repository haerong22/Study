package com.example.book.application.service;

import com.example.book.IntegrationTestSupport;
import com.example.book.adapter.out.jpa.entity.BookJpaEntity;
import com.example.book.adapter.out.jpa.repository.BookJpaRepository;
import com.example.book.application.port.in.command.AddBookCommand;
import com.example.book.domain.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static com.example.book.domain.model.vo.BookStatus.ENTERED;
import static com.example.book.domain.model.vo.Classification.ART;
import static com.example.book.domain.model.vo.Location.PANGYO;
import static com.example.book.domain.model.vo.Source.SUPPLY;
import static org.assertj.core.api.Assertions.assertThat;


class AddBookServiceTest extends IntegrationTestSupport {

    @Autowired
    private AddBookService addBookService;

    @Autowired
    private BookJpaRepository bookJpaRepository;

    @Test
    @DisplayName("도서를 등록 할 수 있다.")
    void addBook() {
        // given
        AddBookCommand command = AddBookCommand.builder()
                .title("test")
                .description("test")
                .author("test")
                .isbn("test")
                .publicationDate(LocalDate.of(2023, 1, 1))
                .source("SUPPLY")
                .classification("ART")
                .location("PANGYO")
                .build();

        // when
        Book result = addBookService.addBook(command);

        // then
        assertThat(result).isNotNull()
                .extracting(
                        "title", "desc.description", "desc.author", "desc.isbn",
                        "desc.publicationDate", "desc.source", "classification", "bookStatus", "location"
                )
                .containsExactly(
                        "test", "test", "test", "test", LocalDate.of(2023, 1, 1),
                        SUPPLY, ART, ENTERED, PANGYO
                );

        BookJpaEntity entity = bookJpaRepository.findById(result.getNo()).get();

        assertThat(entity).isNotNull()
                .extracting(
                        "title", "description", "author", "isbn",
                        "publicationDate", "source", "classification", "bookStatus", "location"
                )
                .containsExactly(
                        "test", "test", "test", "test", LocalDate.of(2023, 1, 1),
                        SUPPLY, ART, ENTERED, PANGYO
                );
    }
}