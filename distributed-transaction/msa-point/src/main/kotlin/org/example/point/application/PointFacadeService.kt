package org.example.point.application

import org.example.point.application.dto.PointReserveCommand
import org.example.point.application.dto.PointReserveConfirmCommand
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.stereotype.Component

@Component
class PointFacadeService(
    private val pointService: PointService,
) {

    fun tryReserve(cmd: PointReserveCommand) {
        var tryCount = 0

        while (tryCount < 3) {
            try {
                return pointService.tryReserve(cmd)
            } catch (e: OptimisticLockingFailureException) {
                tryCount++
            }
        }

        throw RuntimeException("예약에 실패하였습니다.")
    }
    fun confirmReserve(cmd: PointReserveConfirmCommand) {
        var tryCount = 0

        while (tryCount < 3) {
            try {
                return pointService.confirmReserve(cmd)
            } catch (e: OptimisticLockingFailureException) {
                tryCount++
            }
        }

        throw RuntimeException("예약에 실패하였습니다.")
    }
}