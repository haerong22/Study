package org.example.issueservice.service

import org.example.issueservice.domain.Issue
import org.example.issueservice.domain.IssueRepository
import org.example.issueservice.domain.enums.IssueStatus
import org.example.issueservice.exception.NotFoundException
import org.example.issueservice.model.IssueRequest
import org.example.issueservice.model.IssueResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IssueService(
    private val issueRepository: IssueRepository,
) {

    @Transactional
    fun create(userId: Long, request: IssueRequest): IssueResponse {

        val issue = Issue(
            summary = request.summary,
            description = request.description,
            userId = userId,
            type = request.type,
            priority = request.priority,
            status = request.status,
        )

        return IssueResponse(issueRepository.save(issue))
    }

    @Transactional(readOnly = true)
    fun getAll(status: IssueStatus) =
        issueRepository.findAllByStatusOrderByCreatedAtDesc(status)
            .map { IssueResponse(it) }

    @Transactional(readOnly = true)
    fun get(issueId: Long): IssueResponse =
        issueRepository.findByIdOrNull(issueId)
            ?.let { IssueResponse(it) }
            ?: throw NotFoundException("이슈가 존재하지 않습니다.")
}