package org.example.point.application

import org.example.point.application.dto.PointUseCancelCommand
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
        val useHistory =
            pointTransactionHistoryRepository.findByRequestIdAndTransactionType(cmd.requestId, TransactionType.USE)

        if (useHistory != null) {
            println("이미 사용한 이력이 존재합니다.")
            return
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

    @Transactional
    fun cancel(cmd: PointUseCancelCommand) {
        val useHistory =
            pointTransactionHistoryRepository.findByRequestIdAndTransactionType(cmd.requestId, TransactionType.USE)
                ?: return

        val cancelHistory =
            pointTransactionHistoryRepository.findByRequestIdAndTransactionType(cmd.requestId, TransactionType.CANCEL)

        if (cancelHistory != null) {
            println("이미 사용한 이력이 존재합니다.")
            return
        }

        val point = pointRepository.findById(useHistory.pointId).orElseThrow()

        point.cancel(useHistory.amount)

        val history = PointTransactionHistory(
            cmd.requestId,
            point.id!!,
            useHistory.amount,
            TransactionType.CANCEL
        )

        pointTransactionHistoryRepository.save(history)
    }
}