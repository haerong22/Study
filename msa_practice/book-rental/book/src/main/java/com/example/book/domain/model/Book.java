package com.example.book.domain.model;

import com.example.book.domain.model.vo.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    private long no;
    private String title;
    private BookDesc desc;
    private Classification classification;
    private BookStatus bookStatus;
    private Location location;

    @Builder
    private Book(long no, String title, BookDesc desc, Classification classification, BookStatus bookStatus, Location location) {
        this.no = no;
        this.title = title;
        this.desc = desc;
        this.classification = classification;
        this.bookStatus = bookStatus;
        this.location = location;
    }

    public static Book enterBook(
            String title,
            String author,
            String isbn,
            String description,
            LocalDate publicationDate,
            Source source,
            Classification classification,
            Location location
    ) {
        BookDesc bookDesc = BookDesc.builder()
                .description(description)
                .author(author)
                .isbn(isbn)
                .publicationDate(publicationDate)
                .source(source)
                .build();

        return Book.builder()
                .title(title)
                .desc(bookDesc)
                .classification(classification)
                .bookStatus(BookStatus.ENTERED)
                .location(location)
                .build();
    }

    public Book makeAvailable() {
        this.bookStatus = BookStatus.AVAILABLE;
        return this;
    }

    public Book makeUnavailable() {
        this.bookStatus = BookStatus.UNAVAILABLE;
        return this;
    }
}
