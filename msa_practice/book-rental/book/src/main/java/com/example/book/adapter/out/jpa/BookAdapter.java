package com.example.book.adapter.out.jpa;

import com.example.book.adapter.out.jpa.entity.BookJpaEntity;
import com.example.book.adapter.out.jpa.repository.BookJpaRepository;
import com.example.book.application.port.out.BookPort;
import com.example.book.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
@RequiredArgsConstructor
public class BookAdapter implements BookPort {

    private final BookJpaRepository bookJpaRepository;

    @Override
    public Book getBook(long bookNo) {
        return bookJpaRepository.findById(bookNo)
                .orElseThrow(() -> new IllegalArgumentException("도서가 없습니다."))
                .toDomain();
    }

    @Override
    public Book save(Book book) {
        return bookJpaRepository.save(BookJpaEntity.fromDomain(book)).toDomain();
    }
}
