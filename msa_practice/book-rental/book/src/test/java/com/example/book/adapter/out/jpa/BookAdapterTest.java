package com.example.book.adapter.out.jpa;

import com.example.book.adapter.out.jpa.entity.BookJpaEntity;
import com.example.book.adapter.out.jpa.repository.BookJpaRepository;
import com.example.book.domain.model.Book;
import com.example.book.domain.model.vo.BookDesc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static com.example.book.domain.model.vo.BookStatus.ENTERED;
import static com.example.book.domain.model.vo.Classification.COMPUTER;
import static com.example.book.domain.model.vo.Location.JEONGJA;
import static com.example.book.domain.model.vo.Location.PANGYO;
import static com.example.book.domain.model.vo.Source.SUPPLY;
import static org.assertj.core.api.Assertions.*;

@Import({BookAdapter.class})
@DataJpaTest
class BookAdapterTest {

    @Autowired
    private BookJpaRepository bookJpaRepository;

    @Autowired
    private BookAdapter bookAdapter;

    @AfterEach
    void clear() {
        bookJpaRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("도서를 조회할 수 있다.")
    void getBook() {
        // given
        BookJpaEntity entity = createTestBookJpaEntity();
        bookJpaRepository.save(entity);

        // when
        Book result = bookAdapter.getBook(1);

        // then
        assertThat(result).isNotNull()
                .extracting(
                        "no", "title", "desc.description", "desc.author", "desc.isbn",
                        "desc.publicationDate", "desc.source", "classification", "bookStatus", "location"
                )
                .containsExactly(
                        1L, "test", "test", "test", "test", LocalDate.of(2023, 1, 1),
                        SUPPLY, COMPUTER, ENTERED, PANGYO
                );
    }

    @Test
    @DisplayName("해당 도서가 없으면 에러를 응답한다.")
    void getBookNotExist() {
        // given

        // when

        // then
        assertThatThrownBy(() -> bookAdapter.getBook(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("도서가 없습니다.");
    }

    @Test
    @DisplayName("도서를 등록할 수 있다.")
    void save() {
        // given
        Book book = createTestBook();

        // when
        Book result = bookAdapter.save(book);

        // then
        assertThat(result).isNotNull()
                .extracting(
                        "title", "desc.description", "desc.author", "desc.isbn",
                        "desc.publicationDate", "desc.source", "classification", "bookStatus", "location"
                )
                .containsExactly(
                        "엔터프라이즈 아키텍처 패턴", "엔터프라이즈 아키텍처 패턴을 잘 설명해주는 도서", "마틴파울러", "978-89-954321-0-5",
                        LocalDate.of(2023, 1, 1), SUPPLY, COMPUTER, ENTERED, JEONGJA
                );
        assertThat(result.getNo()).isGreaterThan(0);
    }

    public Book createTestBook() {
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

    private BookJpaEntity createTestBookJpaEntity() {
        return BookJpaEntity.builder()
                .no(1)
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