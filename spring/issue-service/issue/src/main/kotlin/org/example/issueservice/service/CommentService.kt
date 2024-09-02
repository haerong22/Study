package org.example.issueservice.service

import org.example.issueservice.domain.Comment
import org.example.issueservice.domain.CommentRepository
import org.example.issueservice.domain.IssueRepository
import org.example.issueservice.exception.NotFoundException
import org.example.issueservice.model.CommentRequest
import org.example.issueservice.model.CommentResponse
import org.example.issueservice.model.toResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val issueRepository: IssueRepository,
) {

    @Transactional
    fun create(issueId: Long, userId: Long, username: String, request: CommentRequest): CommentResponse =
        issueRepository.findByIdOrNull(issueId)
            ?.let { issue ->
                val comment = Comment(
                    issue = issue,
                    userId = userId,
                    username = username,
                    body = request.body,
                )
                issue.comments.add(comment)
                return commentRepository.save(comment).toResponse()
            }
            ?: throw NotFoundException("이슈가 존재하지 않습니다.")

    @Transactional
    fun edit(commentId: Long, userId: Long, request: CommentRequest): CommentResponse =
        commentRepository.findByIdAndUserId(commentId, userId)
            ?.run {
                body = request.body
                commentRepository.save(this).toResponse()
            }
            ?: throw NotFoundException("댓글이 존재하지 않습니다.")
}