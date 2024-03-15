package initialize

/**
 * 인스턴스화 시점과 변수 초기화 시점 분리
 *
 * lateinit 사용
 *
 * -> 초기화 하지 않았을 때 에러가 발생, null 처리 필요 없음
 *
 */
class Person4 {
    lateinit var name: String

    val isKim: Boolean
        get() = this.name.startsWith("김")

    val maskingName: String
        get() = name[0] + (1..<name.length).joinToString("") { "*" }
}