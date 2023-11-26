package com.example.book.application.service;

import com.example.book.application.port.in.MakeAvailableUseCase;
import com.example.book.application.port.in.MakeUnavailableUseCase;
import com.example.book.application.port.out.BookPort;
import com.example.book.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChangeBookStatusService implements MakeAvailableUseCase, MakeUnavailableUseCase {

    private final BookPort bookPort;

    @Override
    public Book available(Long bookNo) {
        Book book = bookPort.getBook(bookNo);
        return bookPort.save(book.makeAvailable());
    }

    @Override
    public Book unavailable(Long bookNo) {
        Book book = bookPort.getBook(bookNo);
        return bookPort.save(book.makeUnavailable());
    }
}
