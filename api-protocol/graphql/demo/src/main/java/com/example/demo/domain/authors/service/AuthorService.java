package com.example.demo.domain.authors.service;

import com.example.demo.domain.authors.entity.Author;
import com.example.demo.domain.authors.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Author saveAuthor(String authorName) {
        Author author = new Author();
        author.setName(authorName);
        return authorRepository.save(author);
    }

    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow();
    }

    public List<Author> findAllById(List<Long> ids) {
        return authorRepository.findAllById(ids);
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}