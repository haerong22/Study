package functions

import kotlin.system.measureTimeMillis

class Functions

fun main() {
//    TODO("main 함수 구현")

    repeat(3) {
        println("Hello world!!")
    }

    val timeMillis =
        measureTimeMillis {
            val result = 1 + 2
        }

    runCatching { 1 / 0 }
}

fun acceptOnlyTow(num: Int) {
    require(num == 2) { "2만 허용!!" }

    if (num != 2) {
        throw IllegalArgumentException("2만 허용!!")
    }
}

class Person {
    val status = PersonStatus.PLAYING

    fun sleep() {
        check(this.status == PersonStatus.PLAYING) { "에러 메세지!!" }

        if (this.status != PersonStatus.PLAYING) {
            throw IllegalStateException()
        }
    }

    enum class PersonStatus {
        PLAYING,
        SLEEPING,
    }
}
