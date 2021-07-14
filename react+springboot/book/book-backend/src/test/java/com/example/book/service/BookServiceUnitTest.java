package com.example.book.service;

import com.example.book.domain.Book;
import com.example.book.domain.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 단위테스트 ( Service 관련 Bean 만 메모리에 띄움 )
 * BookService 는 BookRepository 에 의존적이다.
 * 단위테스트를 위해 BookRepository 를 가짜 객체로 만들어 테스트 ( Mockito )
 */

@ExtendWith(MockitoExtension.class)
class BookServiceUnitTest {

    @InjectMocks // BookService 객체가 만들어질 때 BookServiceUnitTest 파일에 @Mock 으로 등록된 객체를 DI
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void 저장하기_테스트() throws Exception {
        // BODMockito 방식
        // given
        Book book = new Book();
        book.setTitle("제목");
        book.setAuthor("저자");
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        // when
        Book bookEntity = bookService.저장하기(book);

        // then
        Assertions.assertEquals(bookEntity, book);
    }
}