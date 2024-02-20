package ex07

/**
 * data class
 */
fun main() {
    val user = User(name = "홍길동");
    println(user)

    user.age = 20
    user.email = "email@email.com"

    println(user)

    val user2 = user.copy(name = "이순신")
    println(user2)

}