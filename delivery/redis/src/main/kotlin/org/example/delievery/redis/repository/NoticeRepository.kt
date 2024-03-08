package org.example.delievery.redis.repository

import org.example.delievery.redis.common.Log
import org.example.delievery.redis.model.NoticeDto
import org.springframework.stereotype.Repository

@Repository
class NoticeRepository {

    companion object : Log

    private val noticeList = mutableListOf<NoticeDto>()

    fun getNotice(id: Long?): NoticeDto? {
        log.info("repository get notice method call: {}", id)
        return noticeList.firstOrNull { it.id == id }
    }

    fun addNotice(notice: NoticeDto?): NoticeDto? {
        log.info("repository add notice method call: {}", notice)

        val index = noticeList.size + 1

        return notice?.apply {
            id = index.toLong()
            noticeList.add(this)
        }.also {
            log.info("save dto: {}", it)
        }
    }
}