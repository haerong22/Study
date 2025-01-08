package org.example.sociallogin.gateway.db.user

import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface UserR2dbcRepository: R2dbcRepository<UserEntity, Long> {
}