package com.example.book.domain.model;

import com.example.book.domain.model.vo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.example.book.domain.model.vo.BookStatus.*;
import static com.example.book.domain.model.vo.BookStatus.AVAILABLE;
import static com.example.book.domain.model.vo.Classification.*;
import static com.example.book.domain.model.vo.Location.*;
import static com.example.book.domain.model.vo.Source.*;
import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    @Test
    @DisplayName("도서를 생성 할 수 있다.")
    void enterBook() {
        // given
        String title = "제목";
        String author = "저자";
        String isbn = "1234";
        String description = "책 설명";
        LocalDate publicationDate = LocalDate.of(2023, 1, 1);
        Source source = SUPPLY;
        Classification classification = ART;
        Location location = PANGYO;

        // when
        Book book = Book.enterBook(title, author, isbn, description, publicationDate, source, classification, location);

        // then
        assertThat(book).isNotNull()
                .extracting(
                        "title", "desc.description", "desc.author", "desc.isbn",
                        "desc.publicationDate", "desc.source", "classification", "bookStatus", "location"
                )
                .containsExactly(title, description, author, isbn, publicationDate, source, classification, ENTERED, location);
    }

    @Test
    @DisplayName("도서 대여 가능 상태로 변경한다.")
    void makeAvailable() {
        // given
        Book testBook = createTestBook();

        // when
        Book book = testBook.makeAvailable();

        // then
        assertThat(book.getBookStatus()).isEqualTo(AVAILABLE);
    }

    @Test
    @DisplayName("도서 대여 불가능 상태로 변경한다.")
    void makeUnavailable() {
        // given
        Book testBook = createTestBook();

        // when
        Book book = testBook.makeUnavailable();

        // then
        assertThat(book.getBookStatus()).isEqualTo(UNAVAILABLE);
    }

    public static Book createTestBook() {
        return Book.builder()
                .title("엔터프라이즈 아키텍처 패턴")
                .desc(BookDesc.builder()
                        .description("엔터프라이즈 아키텍처 패턴을 잘 설명해주는 도서")
                        .author("마틴파울러")
                        .isbn("978-89-954321-0-5")
                        .publicationDate(LocalDate.of(2023, 1, 1))
                        .source(SUPPLY)
                        .build())
                .classification(COMPUTER)
                .bookStatus(ENTERED)
                .location(JEONGJA)
                .build();
    }
}