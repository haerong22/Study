package com.example.book.adapter.in.web;

import com.example.book.adapter.in.web.response.BookResponse;
import com.example.book.application.port.in.AddBookUseCase;
import com.example.book.application.port.in.InquiryUseCase;
import com.example.book.application.port.in.command.AddBookCommand;
import com.example.book.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final AddBookUseCase addBookUseCase;
    private final InquiryUseCase inquiryUseCase;

    @PostMapping("/api/v1/book")
    public ResponseEntity<BookResponse> createBook(@RequestBody AddBookCommand addBookCommand) {
        Book book = addBookUseCase.addBook(addBookCommand);
        return ResponseEntity.status(CREATED).body(BookResponse.toResponse(book));
    }

    @GetMapping("/api/v1/book/{no}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Long no) {
        return ResponseEntity.ok(BookResponse.toResponse(inquiryUseCase.getBookInfo(no)));
    }
}
