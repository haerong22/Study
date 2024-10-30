package org.example.unittest.article.adapter.in.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.unittest.article.domain.Article;
import org.example.unittest.article.domain.Board;

import java.time.LocalDateTime;

public class ArticleDto {
    public record CreateArticleRequest(
        @NotNull
        Long boardId,
        @NotNull
        String subject,
        @NotNull
        String content,
        @NotEmpty
        String username
    ) { }

    public record UpdateArticleRequest(
        Long id,
        @NotNull
        BoardDto board,
        @NotNull
        String subject,
        @NotNull
        String content,
        @NotEmpty
        String username
    ) { }

    public record ArticleResponse(
        Long id,
        Board board,
        String subject,
        String content,
        String username,
        LocalDateTime createdAt
    ) {
        public static ArticleResponse of(Article article) {
            return new ArticleResponse(
                article.getId(),
                article.getBoard(),
                article.getSubject(),
                article.getContent(),
                article.getUsername(),
                article.getCreatedAt()
            );
        }
    }
}