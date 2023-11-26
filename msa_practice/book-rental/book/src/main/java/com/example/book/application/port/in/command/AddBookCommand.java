package com.example.book.application.port.in.command;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class AddBookCommand {

    private String title;
    private String description;
    private String author;
    private String isbn;
    private LocalDate publicationDate;
    private String source;
    private String classification;
    private String location;
}
