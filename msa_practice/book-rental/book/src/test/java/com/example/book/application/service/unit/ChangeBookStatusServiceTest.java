package com.example.book.application.service.unit;

import com.example.book.application.port.out.BookPort;
import com.example.book.application.service.ChangeBookStatusService;
import com.example.book.domain.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChangeBookStatusServiceTest {

    @Mock
    private BookPort bookPort;

    @InjectMocks
    private ChangeBookStatusService changeBookStatusService;

    @Test
    @DisplayName("도서 대여 가능 상태로 변경 할 수 있다.")
    void available() {
        // given
        Long bookNo = 1L;

        when(bookPort.getBook(anyLong())).thenReturn(Book.builder().build());
        when(bookPort.save(any(Book.class))).thenReturn(Book.builder().build());

        // when
        changeBookStatusService.available(bookNo);

        // then
        verify(bookPort, times(1)).getBook(1L);
        verify(bookPort, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("도서 대여 불가능 상태로 변경 할 수 있다.")
    void unavailable() {
        // given
        Long bookNo = 1L;

        when(bookPort.getBook(anyLong())).thenReturn(Book.builder().build());
        when(bookPort.save(any(Book.class))).thenReturn(Book.builder().build());

        // when
        changeBookStatusService.unavailable(bookNo);

        // then
        verify(bookPort, times(1)).getBook(1L);
        verify(bookPort, times(1)).save(any(Book.class));
    }

}