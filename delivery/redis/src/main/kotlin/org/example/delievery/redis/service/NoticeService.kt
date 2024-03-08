package org.example.delievery.redis.service

import org.example.delievery.redis.common.Log
import org.example.delievery.redis.model.NoticeDto
import org.example.delievery.redis.repository.NoticeRepository
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class NoticeService(
    private val noticeRepository: NoticeRepository
) {

    companion object : Log

    @Cacheable(cacheNames = ["notice"], key = "#id")
    fun getNotice(id: Long?): NoticeDto? {
        log.info("service get notice method call: {}", id)
        return noticeRepository.getNotice(id)
    }

    @CachePut(cacheNames = ["notice"], key = "#notice.id")
    fun addNotice(notice: NoticeDto?): NoticeDto? {
        log.info("service add notice method call: {}", notice)
        return noticeRepository.addNotice(notice)
    }
}