package com.example.refactoring._13_loops._33_replace_loop_with_pipeline;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorTest {

    @Test
    void twitterHandler() {
        Author kim = new Author("ms", null);
        Author lee = new Author("naver", "lee");
        assertEquals(List.of("lee"), Author.TwitterHandles(List.of(kim, lee), "naver"));
    }


}