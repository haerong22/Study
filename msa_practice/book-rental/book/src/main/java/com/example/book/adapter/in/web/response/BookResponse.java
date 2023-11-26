package com.example.book.adapter.in.web.response;

import com.example.book.domain.model.Book;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookResponse {

    private long bookNo;
    private String bookTitle;
    private String bookStatus;

    public static BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .bookNo(book.getNo())
                .bookTitle(book.getTitle())
                .bookStatus(book.getBookStatus().name())
                .build();
    }
}
