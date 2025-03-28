package org.example.moviedgs.repositories

import org.example.moviedgs.entities.Director
import org.springframework.data.jpa.repository.JpaRepository

interface DirectorRepository: JpaRepository<Director, Long> {
}