package org.example.point.controller

import org.example.point.application.PointService
import org.example.point.application.RedisLockService
import org.example.point.controller.dto.PointUseCancelRequest
import org.example.point.controller.dto.PointUseRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PointController(
    private val pointService: PointService,
    private val redisLockService: RedisLockService,
) {

    @PostMapping("/points/use")
    fun use(
        @RequestBody request: PointUseRequest
    ) {
        val key = "point:orchestration:${request.requestId}"

        val acquiredLock = redisLockService.tryLock(key, request.requestId)

        if (!acquiredLock) {
            throw RuntimeException("락 획득에 실패하였습니다.")
        }

        try {
            pointService.use(request.toCommand())
        } finally {
            redisLockService.releaseLock(key)
        }
    }

    @PostMapping("/points/use/cancel")
    fun cancel(
        @RequestBody request: PointUseCancelRequest
    ) {
        val key = "point:orchestration:${request.requestId}"

        val acquiredLock = redisLockService.tryLock(key, request.requestId)

        if (!acquiredLock) {
            throw RuntimeException("락 획득에 실패하였습니다.")
        }

        try {
            pointService.cancel(request.toCommand())
        } finally {
            redisLockService.releaseLock(key)
        }
    }
}