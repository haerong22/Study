package org.example.moviedgs.repositories

import org.example.moviedgs.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
}