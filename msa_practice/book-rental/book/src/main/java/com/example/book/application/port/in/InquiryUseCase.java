package com.example.book.application.port.in;

import com.example.book.domain.model.Book;

public interface InquiryUseCase {

    Book getBookInfo(long bookNo);
}
