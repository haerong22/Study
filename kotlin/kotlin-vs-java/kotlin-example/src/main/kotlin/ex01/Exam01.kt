package ex01

/**
 * 변수 선언
 */
fun main() {
    // var => mutable, val => immutable (=final)
    // 타입 생략 가능

    // primitive 타입 없음
    val i = 10;
    val _i: Int = 10;

    var d: Double = 20.0;
    var _d: Double;

    var f: Float = 10f;

    var name: String = "홍길동";
    val _name: String = "홍길동";
    val n = "홍길동";

    println("사용자 이름: $name");
}