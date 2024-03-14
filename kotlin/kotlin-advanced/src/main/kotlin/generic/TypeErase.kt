package generic

/**
 * 제네릭 타입은 런타임에서 타입이 소거 된다.(타입정보가 사라진다.)
 * -> 하위 jdk 호환성
 */
fun main() {
    val num = 3
    num.toSuperString() // "Int: 3"

    val str = "ABC"
    str.toSuperString() // "String: ABC"
}

//fun <T> T.toSuperString() {
//    // 런타임에는 T를 알 수 없으므로 오류가 발생
//    println("${T::class.simpleName}: $this")
//}

inline fun <reified T> T.toSuperString() {
    println("${T::class.simpleName}: $this")
}

inline fun <reified T> List<*>.hasAnyInstanceOf(): Boolean {
    return this.any { it is T }
}

class TypeErase
