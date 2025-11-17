package org.example.point.init

import jakarta.annotation.PostConstruct
import org.example.point.domain.Point
import org.example.point.infrastructure.PointRepository
import org.springframework.stereotype.Component

@Component
class TestDataCreator(
    private val pointRepository: PointRepository
) {

    @PostConstruct
    fun createTestData() {
        pointRepository.save(Point(1, 10000))
    }
}