package ex11

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * run
 */
fun main() {

    val userDto = UserDto("").run {
        name = "홍길동"
        "철수"
    }

    println("result: $userDto")

    val sum = run {
        val x = 2
        val y = 3
        x + y
    }

    println(sum)

    val now: LocalDateTime? = null

    val n = now ?: LocalDateTime.now()

    val d = now?.let {
        val minus = it.minusDays(1)
        minus.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    }?: run {
        val now = LocalDateTime.now()
        val minus = now.minusDays(1)
        minus.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    }

    println(d)
}