package ex11

/**
 * also
 */
fun main() {

    val userDto = UserDto(
        name = "홍길동"
    ).also {// 자기 자신 리턴
        println(it)
    }

    println("result $userDto")
}