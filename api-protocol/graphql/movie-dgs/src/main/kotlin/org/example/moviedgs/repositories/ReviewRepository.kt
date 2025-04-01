package org.example.moviedgs.repositories

import org.example.moviedgs.entities.Movie
import org.example.moviedgs.entities.Review
import org.example.moviedgs.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Long> {

    fun findAllByMovie(movie: Movie): List<Review>

    fun findAllByUser(user: User): List<Review>
}