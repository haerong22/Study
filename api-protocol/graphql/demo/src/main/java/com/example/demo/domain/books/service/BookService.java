package com.example.demo.domain.books.service;

import com.example.demo.domain.authors.repository.AuthorRepository;
import com.example.demo.domain.books.entity.Book;
import com.example.demo.domain.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public Book saveBook(String title, String publisher, LocalDate publishedDate, List<Long> authorIds) {
        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setPublisher(publisher);
        newBook.setPublishedDate(publishedDate);
        newBook.setAuthors(new HashSet<>(authorRepository.findAllById(authorIds)));
        return bookRepository.save(newBook);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findByAuthorName(String authorName) {
        return bookRepository.findByAuthorsName(authorName);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}