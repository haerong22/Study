package ex02

/**
 * Null 안정성 및 엘비스 연산자
 */
fun main() {

    val a: Int
    val b = 10
    val c: Int = 20
    val d: Int? = null // ? => 엘비스 연산자 null 가능

//    println(a)
    callFunction(b)
    callFunction(c)
    callFunction(d)
}

fun callFunction(i: Int?) {
    // ? => null 이 올 수 있다.
    // 변수? => 변수가 null 인가?
    // 변수?. => 변수가 null 이 아닐 때
    // 변수?: => 변수가 null 일 때

    val temp = i ?: "null 값 입니다."
    println(temp)
}