package org.example.delievery.redis.controller

import org.example.delievery.redis.common.Log
import org.example.delievery.redis.model.NoticeDto
import org.example.delievery.redis.service.NoticeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notice")
class NoticeController(
    private val noticeService: NoticeService
) {
    companion object : Log

    @GetMapping
    fun getNotice(
        @RequestParam id: Long?
    ): NoticeDto? {
        log.info("controller get notice method call: {}", id)
        return noticeService.getNotice(id)
    }

    @PostMapping
    fun addNotice(
        @RequestParam notice: String?
    ): NoticeDto? {
        log.info("controller add notice method call: {}", notice)
        return noticeService.addNotice(NoticeDto(notice = notice))
    }
}