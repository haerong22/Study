package org.example.user

import org.example.model.UserDto
import java.time.LocalDateTime

class UserService {

    fun logic(userDto: UserDto? = null) {
        val userDto = UserDto(
            null,
            20,
            "email@email.com",
            "01012341234",
            LocalDateTime.now(),
        )

        userDto.name?.let {
            println(it)
        } ?: println("null")

        println(userDto)
    }
}

fun main() {
    UserService().logic()
}