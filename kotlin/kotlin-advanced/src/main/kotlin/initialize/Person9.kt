package initialize

import kotlin.properties.Delegates

/**
 * 표준 위임 객체
 *
 * vetoable()
 *
 */
class Person9 {
    var age: Int by Delegates.vetoable(20) { _, _, newValue -> newValue >= 1 }
}

fun main() {
    val p = Person9()
    p.age = 30
    println(p.age)
    p.age = -10
    println(p.age)
}