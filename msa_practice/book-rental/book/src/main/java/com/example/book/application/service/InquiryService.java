package com.example.book.application.service;

import com.example.book.application.port.in.InquiryUseCase;
import com.example.book.application.port.out.BookPort;
import com.example.book.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InquiryService implements InquiryUseCase {

    private final BookPort bookPort;

    @Override
    public Book getBookInfo(long bookNo) {
        return bookPort.getBook(bookNo);
    }
}
