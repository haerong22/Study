package org.example.point.application

import org.example.point.application.dto.PointUseCommand
import org.example.point.domain.PointTransactionHistory
import org.example.point.domain.TransactionType
import org.example.point.infrastructure.PointRepository
import org.example.point.infrastructure.PointTransactionHistoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PointService(
    private val pointRepository: PointRepository,
    private val pointTransactionHistoryRepository: PointTransactionHistoryRepository,
) {

    @Transactional
    fun use(cmd: PointUseCommand) {
        val useHistory = pointTransactionHistoryRepository.findByRequestIdAndTransactionType(cmd.requestId, TransactionType.USE)

        if (useHistory != null) {
            println("이미 사용한 이력이 존재합니다.")
        }

        val point = pointRepository.findByUserId(cmd.userId) ?: throw RuntimeException("포인트가 존재하지 않습니다.")

        point.use(cmd.amount)

        val history = PointTransactionHistory(
            cmd.requestId,
            point.id!!,
            cmd.amount,
            TransactionType.USE
        )
        pointTransactionHistoryRepository.save(history)
    }
}