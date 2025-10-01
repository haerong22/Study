package org.example.monolithic.point.infrastructure

import org.example.monolithic.point.domain.Point
import org.springframework.data.jpa.repository.JpaRepository

interface PointRepository: JpaRepository<Point, Long> {

    fun findByUserId(userId: Long): Point?
}