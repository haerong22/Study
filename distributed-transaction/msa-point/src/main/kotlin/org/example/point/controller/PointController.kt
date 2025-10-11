package org.example.point.controller

import org.example.point.application.PointFacadeService
import org.example.point.application.RedisLockService
import org.example.point.controller.dto.PointReserveConfirmRequest
import org.example.point.controller.dto.PointReserveRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PointController(
    private val pointFacadeService: PointFacadeService,
    private val redisLockService: RedisLockService,
) {

    @PostMapping("/points/reserve")
    fun reserve(
        @RequestBody request: PointReserveRequest,
    ) {
        val key = "point:${request.requestId}"
        val acquiredLock = redisLockService.tryLock(key, request.requestId)

        if (!acquiredLock) {
            throw RuntimeException("락 획득에 실패하였습니다.")
        }

        try {
            pointFacadeService.tryReserve(request.toCommand())
        } finally {
            redisLockService.releaseLock(key)
        }
    }

    @PostMapping("/points/confirm")
    fun confirm(
        @RequestBody request: PointReserveConfirmRequest,
    ) {
        val key = "point:${request.requestId}"
        val acquiredLock = redisLockService.tryLock(key, request.requestId)

        if (!acquiredLock) {
            throw RuntimeException("락 획득에 실패하였습니다.")
        }

        try {
            pointFacadeService.confirmReserve(request.toCommand())
        } finally {
            redisLockService.releaseLock(key)
        }
    }
}