package ex11

import java.time.LocalDateTime
import kotlin.streams.toList

/**
 * let
 */
fun main() {

//    val now: LocalDateTime? = null
    val now = LocalDateTime.now()

    val kst = now?.let { localDateTime: LocalDateTime ->
        println("let scope $localDateTime")
        "let"
    } ?: LocalDateTime.now()

    println("result $kst")

    // 마지막 라인 리턴
    UserDto(name = "홍길동").let {
        logic(it)
    }

    val userDtoList = listOf(UserDto("홍길동"), UserDto("철수"))

    val responseList = userDtoList.stream()
        .map {
            UserResponse(
                userName = it.name
            )
        }.toList()

    println(responseList)
}

fun logic(userDto: UserDto?): UserResponse? {

    return userDto?.let {
        println(it)

        UserEntity(
            name = it.name
        )
    }?.let {
        println(it)

        UserResponse(
            userName = it.name
        )
    }
}

data class UserDto(
    var name: String? = null,
)

data class UserEntity(
    var name: String? = null,
)

data class UserResponse(
    var userName: String? = null,
)