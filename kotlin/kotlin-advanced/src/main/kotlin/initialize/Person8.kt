package initialize

import kotlin.properties.Delegates

/**
 * 표준 위임 객체
 *
 * observable()
 *
 */
class Person8 {
    var age: Int by Delegates.observable(20) { _, oldValue, newValue ->
        println("이전 값 : ${oldValue}, 새로운 값 : ${newValue}")
    }
}

fun main() {
    val p = Person8()
    p.age = 30
    p.age = 30
}