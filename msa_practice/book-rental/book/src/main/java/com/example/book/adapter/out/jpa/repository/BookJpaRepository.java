package com.example.book.adapter.out.jpa.repository;

import com.example.book.adapter.out.jpa.entity.BookJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookJpaRepository extends JpaRepository<BookJpaEntity, Long> {
}
