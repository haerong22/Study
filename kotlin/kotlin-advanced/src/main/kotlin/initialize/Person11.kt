package initialize

/**
 * 표준 위임 객체
 *
 * Map
 */
class Person11(map: Map<String, Any>) {
    val name: String by map
    val age: Int by map
}

fun main() {
    val p = Person11(mapOf("name" to "홍길동"))
    println(p.name)
    println(p.age) // 에외 발생 주의
}
