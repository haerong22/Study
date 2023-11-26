package com.example.book.application.service.unit;

import com.example.book.application.port.in.command.AddBookCommand;
import com.example.book.application.port.out.BookPort;
import com.example.book.application.service.AddBookService;
import com.example.book.domain.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddBookServiceTest {

    @Mock
    private BookPort bookPort;

    @InjectMocks
    private AddBookService addBookService;

    @Test
    @DisplayName("도서를 추가 할 수 있다.")
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

        when(bookPort.save(any(Book.class))).thenReturn(Book.builder().build());

        // when
        addBookService.addBook(command);

        // then
        verify(bookPort, times(1)).save(any(Book.class));
    }

}