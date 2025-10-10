package org.example.point.application

import org.example.point.application.dto.PointReserveCommand
import org.example.point.domain.PointReservation
import org.example.point.infrastructure.PointRepository
import org.example.point.infrastructure.PointReservationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PointService(
    private val pointRepository: PointRepository,
    private val pointReservationRepository: PointReservationRepository,
) {

    @Transactional
    fun tryReserve(cmd: PointReserveCommand) {
        val reservation = pointReservationRepository.findByRequestId(cmd.requestId)

        if (reservation != null) {
            println("이미 예약된 요청입니다.")
            return
        }

        val point = pointRepository.findByUserId(cmd.userId) ?: throw RuntimeException("유저가 없습니다.")
        point.reserve(cmd.reserveAmount)

        pointReservationRepository.save(
            PointReservation(
                cmd.requestId,
                point.id!!,
                cmd.reserveAmount
            )
        )
    }
}