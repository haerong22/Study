package com.example.book.adapter.in.web;

import com.example.book.adapter.in.web.response.BookResponse;
import com.example.book.application.port.in.AddBookUseCase;
import com.example.book.application.port.in.command.AddBookCommand;
import com.example.book.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final AddBookUseCase addBookUseCase;

    @PostMapping("/api/v1/book")
    public ResponseEntity<BookResponse> createBook(@RequestBody AddBookCommand addBookCommand) {
        Book book = addBookUseCase.addBook(addBookCommand);
        return ResponseEntity.status(CREATED).body(BookResponse.toResponse(book));
    }
}
