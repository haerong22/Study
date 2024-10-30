package org.example.unittest.article.application.port.in;

import org.example.unittest.article.domain.Article;

import java.util.List;

public interface GetArticleUseCase {
    Article getArticleById(Long articleId);

    List<Article> getArticlesByBoard(Long boardId);
}