package org.example.point.infrastructure

import org.example.point.domain.Point
import org.springframework.data.jpa.repository.JpaRepository

interface PointRepository: JpaRepository<Point, Long> {

    fun findByUserId(userId: Long): Point?
}