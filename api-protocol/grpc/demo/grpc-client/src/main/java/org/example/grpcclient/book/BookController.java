package org.example.grpcclient.book;

import lombok.RequiredArgsConstructor;
import org.example.grpcclient.utils.JsonConverter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookGrpcClient bookGrpcClient;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBookList() throws IOException {
        String result = JsonConverter.toJsonList(bookGrpcClient.findAll());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBookById(@PathVariable("id") Long bookId) throws IOException {
        String result = JsonConverter.toJson(bookGrpcClient.findById(bookId));
        return ResponseEntity.ok().body(result);
    }
}
