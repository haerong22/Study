package initialize

/**
 * 표준 위임 객체
 *
 * 또 다른 프로퍼티로 위임
 *
 */
class Person10 {
    @Deprecated("age를 사용하세요!!", ReplaceWith("age"))
    var num: Int = 0
    var age: Int by this::num
}

fun main() {
    val p = Person10()
    p.num = 30
    println(p.age)
    p.age = 20
    println(p.age)
}