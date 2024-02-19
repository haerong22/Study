package com.example.refactoring._13_loops._33_replace_loop_with_pipeline;

import java.util.List;
import java.util.Objects;

public class Author {

    private String company;

    private String twitterHandle;

    public Author(String company, String twitterHandle) {
        this.company = company;
        this.twitterHandle = twitterHandle;
    }

    static public List<String> TwitterHandles(List<Author> authors, String company) {
        return authors.stream()
                .filter(author -> author.company.equals(company) && Objects.nonNull(author.twitterHandle))
                .map(author -> author.twitterHandle)
                .toList();
    }

}
