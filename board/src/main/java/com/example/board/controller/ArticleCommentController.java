package com.example.board.controller;

import com.example.board.dto.UserAccountDto;
import com.example.board.dto.request.ArticleCommentRequest;
import com.example.board.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest) {

        // TODO: 인증 정보 필요
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(
                UserAccountDto.of(
                        "bobby",
                        "1234",
                        "bobby@email.com",
                        null,
                        null
                )
        ));

        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId,
                                       Long articleId) {

        articleCommentService.deleteArticleComment(commentId);

        return "redirect:/articles/" + articleId;
    }
}
