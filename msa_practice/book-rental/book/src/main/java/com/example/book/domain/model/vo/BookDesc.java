package com.example.book.domain.model.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookDesc {

    private String description;
    private String author;
    private String isbn;
    private LocalDate publicationDate;
    private Source source;
}
