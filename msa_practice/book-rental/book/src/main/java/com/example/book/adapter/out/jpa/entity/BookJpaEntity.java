package com.example.book.adapter.out.jpa.entity;

import com.example.book.domain.model.Book;
import com.example.book.domain.model.vo.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class BookJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;

    private String title;
    private String description;
    private String author;
    private String isbn;
    private LocalDate publicationDate;

    @Enumerated(EnumType.STRING)
    private Source source;

    @Enumerated(EnumType.STRING)
    private Classification classification;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    @Enumerated(EnumType.STRING)
    private Location location;

    public Book toDomain() {
        return Book.builder()
                .no(no)
                .title(title)
                .desc(
                        BookDesc.builder()
                                .description(description)
                                .author(author)
                                .isbn(isbn)
                                .publicationDate(publicationDate)
                                .source(source)
                                .build()
                )
                .classification(classification)
                .bookStatus(bookStatus)
                .location(location)
                .build();
    }

    public static BookJpaEntity fromDomain(Book book) {
        return BookJpaEntity.builder()
                .no(book.getNo())
                .title(book.getTitle())
                .description(book.getDesc().getDescription())
                .author(book.getDesc().getAuthor())
                .isbn(book.getDesc().getIsbn())
                .publicationDate(book.getDesc().getPublicationDate())
                .source(book.getDesc().getSource())
                .classification(book.getClassification())
                .bookStatus(book.getBookStatus())
                .location(book.getLocation())
                .build();
    }

}

