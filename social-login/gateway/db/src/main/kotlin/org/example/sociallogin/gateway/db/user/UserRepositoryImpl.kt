package org.example.sociallogin.gateway.db.user

import org.example.sociallogin.domain.user.User
import org.example.sociallogin.domain.user.UserRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class UserRepositoryImpl(
    private val userR2dbcRepository: UserR2dbcRepository,
): UserRepository {

    override fun save(user: User): Mono<User> {
        val userEntity = UserEntity(
            name = user.name,
            email = user.email,
            socialId = user.socialId,
            provider = user.provider,
        )
        return userR2dbcRepository.save(userEntity)
            .map { it.toDomain() }
    }
}