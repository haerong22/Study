package org.example.unittest.article.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.BDDAssertions.then;

class ArticleTest {

    @Test
    @DisplayName("Article constructor")
    void constructArticle() {
        var board = new Board(5L, "board", BoardType.GENERAL);

        var article = Article.builder()
                .id(1L)
                .board(board)
                .subject("subject")
                .content("content")
                .username("user")
                .createdAt(LocalDateTime.now())
                .build();

        then(article)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("board.id", 5L)
                .hasFieldOrPropertyWithValue("subject", "subject")
                .hasFieldOrPropertyWithValue("content", "content")
                .hasFieldOrProperty("createdAt");
    }

    @Test
    @DisplayName("Article update")
    void updateArticle() {
        // Given
        var article = Article.builder()
                .id(1L)
                .board(new Board(5L, "board", BoardType.GENERAL))
                .subject("subject")
                .content("content")
                .username("user")
                .createdAt(LocalDateTime.now())
                .build();

        // When
        article.update("new subject", "new content");

        // Assert
        then(article)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("board.id", 5L)
                .hasFieldOrPropertyWithValue("subject", "new subject")
                .hasFieldOrPropertyWithValue("content", "new content");
    }
}