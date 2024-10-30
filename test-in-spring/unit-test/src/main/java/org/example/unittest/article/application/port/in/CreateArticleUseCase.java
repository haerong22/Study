package org.example.unittest.article.application.port.in;


import org.example.unittest.article.adapter.in.api.dto.ArticleDto;
import org.example.unittest.article.domain.Article;

public interface CreateArticleUseCase {
    Article createArticle(ArticleDto.CreateArticleRequest request);
}