package com.example.board.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record HashtagWithArticlesDto(

        Long id,
        Set<ArticleDto> articles,
        String hashtagName,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
}
