package com.example.demo.domain.authors.controller;

import com.example.demo.domain.authors.entity.Author;
import com.example.demo.domain.authors.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @QueryMapping
    public List<Author> getAllAuthors() {
        return authorService.findAll();
    }

    @QueryMapping
    public Author getAuthorById(@Argument Long id) {
        return authorService.findById(id);
    }


    @MutationMapping
    public Author addAuthor(@Argument String authorName) {
        return authorService.saveAuthor(authorName);
    }
}
