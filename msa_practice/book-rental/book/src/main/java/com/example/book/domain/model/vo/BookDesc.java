package com.example.book.domain.model.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookDesc {

    private final String description;
    private final String author;
    private final String isbn;
    private final LocalDate publicationDate;
    private final Source source;

    @Builder
    private BookDesc(String description, String author, String isbn, LocalDate publicationDate, Source source) {
        this.description = description;
        this.author = author;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.source = source;
    }
}
