package com.example.webflux.r2dbc.service

import com.example.webflux.r2dbc.common.EmptyImage
import com.example.webflux.r2dbc.common.Image
import com.example.webflux.r2dbc.common.User
import com.example.webflux.r2dbc.common.repository.AuthEntity
import com.example.webflux.r2dbc.common.repository.UserEntity
import com.example.webflux.r2dbc.repository.UserR2dbcRepository
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.Map
import java.util.Optional

@Service
class UserCoroutineService(
    private val userRepository: UserR2dbcRepository,
    private val entityTemplate: R2dbcEntityTemplate,
) {

    private val webClient = WebClient.create("http://localhost:8081")

    fun findById(userId: String): Mono<User> {
        return userRepository.findById(userId.toLong())
            .flatMap { userEntity: UserEntity ->
                val imageId = userEntity.profileImageId
                val uriVariableMap = Map.of("imageId", imageId)
                webClient.get()
                    .uri("/api/images/{imageId}", uriVariableMap)
                    .retrieve()
                    .bodyToMono(ImageResponse::class.java)
                    .map { imageResp: ImageResponse -> Image(imageResp.id, imageResp.name, imageResp.url) }
                    .switchIfEmpty(Mono.just(EmptyImage()))
                    .map { image: Image ->
                        var profileImage: Optional<Image> = Optional.empty()
                        if (image !is EmptyImage) { profileImage = Optional.of(image) }
                        map(userEntity, profileImage)
                    }
            }
    }

    @Transactional
    fun createUser(name: String, age: Int, password: String, profileImageId: String): Mono<User> {
        val newUser = UserEntity(name, age, profileImageId, password)
        return userRepository.save(newUser)
            .flatMap { userEntity: UserEntity ->
                val token = generateRandomToken()
                val auth = AuthEntity(userEntity.id, token)
                entityTemplate.insert(auth).map { userEntity }
            }
            .map { userEntity: UserEntity -> map(userEntity, Optional.of(EmptyImage())) }
    }

    private fun map(userEntity: UserEntity, profileImage: Optional<Image>): User {
        return User(
            userEntity.id.toString(),
            userEntity.name,
            userEntity.age,
            profileImage,
            listOf(),
            0L
        )
    }

    private fun generateRandomToken(): String {
        val token = StringBuilder()
        for (i in 0..5) {
            val item = ('A'.code.toDouble() + (Math.random() * 26)).toInt().toChar()
            token.append(item)
        }

        return token.toString()
    }
}