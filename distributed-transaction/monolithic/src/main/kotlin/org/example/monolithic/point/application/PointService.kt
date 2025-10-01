package org.example.monolithic.point.application

import org.example.monolithic.point.infrastructure.PointRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PointService(
    private val pointRepository: PointRepository,
) {

    @Transactional
    fun use(userId: Long, amount: Long) {
        val point = pointRepository.findByUserId(userId) ?: throw RuntimeException("포인트가 존재하지 않습니다.")
        point.use(amount)
    }
}