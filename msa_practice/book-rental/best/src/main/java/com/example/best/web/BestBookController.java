package com.example.best.web;

import com.example.best.domain.model.BestBook;
import com.example.best.domain.service.BestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BestBookController {

    private final BestBookService bestBookService;

    @GetMapping("/api/v1/books")
    public ResponseEntity<List<BestBook>> getAllBooks() {
        return ResponseEntity.ok(bestBookService.getAllBooks());
    }

    @GetMapping("/api/v1/books/{id}")
    public ResponseEntity<BestBook> getBookById(@PathVariable String id) {
        Optional<BestBook> optional = bestBookService.getBookById(id);
        return optional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/v1/books")
    public ResponseEntity<BestBook> createBook(@RequestBody BestBook book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bestBookService.saveBook(book));
    }

    @PutMapping("/api/v1/books/{id}")
    public ResponseEntity<BestBook> updateBook(@PathVariable String id, @RequestBody BestBook book) {
        BestBook updated = bestBookService.update(id, book);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }
}
