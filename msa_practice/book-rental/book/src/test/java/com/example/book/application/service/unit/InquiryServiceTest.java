package com.example.book.application.service.unit;

import com.example.book.application.port.out.BookPort;
import com.example.book.application.service.InquiryService;
import com.example.book.domain.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InquiryServiceTest {

    @Mock
    private BookPort bookPort;

    @InjectMocks
    private InquiryService inquiryService;

    @Test
    @DisplayName("도서 정보를 조회 할 수 있다.")
    void getBookInfo() {
        // given
        long bookNo = 1;
        when(bookPort.getBook(bookNo)).thenReturn(Book.builder().build());

        // when
        inquiryService.getBookInfo(1);

        // then
        verify(bookPort, times(1)).getBook(1);
    }
}