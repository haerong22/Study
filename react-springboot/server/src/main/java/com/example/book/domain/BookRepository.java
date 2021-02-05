package com.example.book.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 생략가능
public interface BookRepository extends JpaRepository<Book, Long> {
}
