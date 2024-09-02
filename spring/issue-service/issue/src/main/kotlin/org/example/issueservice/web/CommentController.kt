package org.example.issueservice.web

import org.example.issueservice.config.AuthUser
import org.example.issueservice.model.CommentRequest
import org.example.issueservice.model.CommentResponse
import org.example.issueservice.service.CommentService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/issues/{issueId}/comments")
class CommentController(
    private val commentService: CommentService,
) {

    @PostMapping
    fun create(
        authUser: AuthUser,
        @PathVariable issueId: Long,
        @RequestBody request: CommentRequest,
    ): CommentResponse =
        commentService.create(issueId, authUser.userId, authUser.username, request)

    @PutMapping("/{commentId}")
    fun edit(
        authUser: AuthUser,
        @PathVariable commentId: Long,
        @RequestBody request: CommentRequest,
    ): CommentResponse =
        commentService.edit(commentId, authUser.userId, request)
}