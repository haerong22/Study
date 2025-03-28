package org.example.moviedgs.repositories

import org.example.moviedgs.entities.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository: JpaRepository<Review, Long> {
}