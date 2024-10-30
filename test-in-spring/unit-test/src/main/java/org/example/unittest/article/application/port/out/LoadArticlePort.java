package org.example.unittest.article.application.port.out;

import org.example.unittest.article.domain.Article;

import java.util.List;
import java.util.Optional;

public interface LoadArticlePort {
    Optional<Article> findArticleById(Long articleId);
    List<Article> findArticlesByBoardId(Long boardId);
}