package com.example.book.application.port.out;

import com.example.book.domain.model.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookPort {

    Book getBook(long bookNo);

    Book save(Book book);
}
