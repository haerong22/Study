package initialize

/**
 * 인스턴스화 시점과 변수 초기화 시점 분리
 *
 * name의 기본값 정의
 *
 * -> 하지만 인스턴스화 후 name을 초기화 하지 않더라도 예외가 발생하지 않아 위험
 */
class Person2 {
    var name: String = "홍길동"

    val isKim: Boolean
        get() = this.name.startsWith("김")

    val maskingName: String
        get() = name[0] + (1..<name.length).joinToString("") { "*" }
}