package initialize

/**
 * 인스턴스화 시점과 변수 초기화 시점 분리
 *
 * name을 nullable로 처리
 *
 * -> 초기화 하지 않았을 때 에러가 발생, 하지만 null 처리가 필요(?. / ?: / !!)
 */
class Person3 {
    var name: String? = null

    val isKim: Boolean
        get() = this.name!!.startsWith("김")

    val maskingName: String
        get() = name!![0] + (1..<name!!.length).joinToString("") { "*" }
}
