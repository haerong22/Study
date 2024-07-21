package com.example.demo.domain.books.controller;

import com.example.demo.domain.books.entity.Book;
import com.example.demo.domain.books.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @QueryMapping
    public Book getBookById(@Argument Long id) {
        return bookService.findById(id);
    }

    @QueryMapping
    public List<Book> getBooks() {
        return bookService.findAll();
    }

    @QueryMapping
    public List<Book> getBooksByAuthorName(@Argument String authorName) {
        return bookService.findByAuthorName(authorName);
    }

    @MutationMapping
    public Book addBook(
            @Argument String title,
            @Argument String publisher,
            @Argument LocalDate publishedDate,
            @Argument List<Long> authorIds
    ) {
        return bookService.saveBook(title, publisher, publishedDate, authorIds);
    }

    @MutationMapping
    public Map<String, Boolean> deleteBook(@Argument Long id) {
        bookService.deleteById(id);

        Map<String, Boolean> resultMap = new HashMap<>();
        resultMap.put("success", true);

        return resultMap;
    }
}
