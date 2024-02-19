
var x: Int = 5 // 탑 레벨에 변수 할당 가능

fun main() {
    x += 1
    println(x)

    val a: Int = 1

    val b = 1 // 타입 생략 가능

    val c: Int // 지연 할당 시 타입 생략 불가
    c = 3

    // val(value)     : 불변
    // var(variable)  : 가변
    val d: String = "Hello"
    var e: String = "Hello"
    e = "World"

    var f = 123 // 타입이 고정되면 다른 타입으로 할당 불가

}