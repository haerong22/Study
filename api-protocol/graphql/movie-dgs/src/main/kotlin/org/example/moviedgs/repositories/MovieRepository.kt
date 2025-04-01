package org.example.moviedgs.repositories

import org.example.moviedgs.entities.Director
import org.example.moviedgs.entities.Movie
import org.springframework.data.jpa.repository.JpaRepository

interface MovieRepository: JpaRepository<Movie, Long> {

    fun findAllByDirector(director: Director): List<Movie>
}