package ex09

import java.lang.NullPointerException
import java.lang.RuntimeException

/**
 * when
 */
fun main() {
    val storeUser = StoreUser()
    println(storeUser.role)

    val result: String = when(storeUser.role) {
        "MASTER" -> {
            "master"
        }
        "ADMIN" -> {
            "admin"
        }
        "USER" -> {
            "user"
        }
        else -> {
            "guest"
        }
    }

    println(result)

    var exception = RuntimeException()

    when(exception) {
        is RuntimeException -> {

        }
        is NullPointerException -> {

        }
    }

    var number = 10

    when(val n = number % 2) {
        0 -> {
            println("짝")
        }
        else -> {
            println("홀")
        }
    }

    val result2:Int = when {
        number % 2 == 0 -> {
            100
        }
        else -> {
            200
        }
    }

    println(result2)
}

data class StoreUser(
    var name: String?= "guest",
    var role: String?= "guest",
)