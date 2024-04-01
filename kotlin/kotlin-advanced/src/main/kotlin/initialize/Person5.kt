package initialize

/**
 * 인스턴스화 시점과 변수 초기화 시점 분리
 *
 * backing property
 *
 * -> 초기화 과정이 비싼 작업인 경우 초기화를 한번만 실행, 코드 작성이 길다..
 *
 */
class Person5 {
    private var _name: String? = null
    val name: String
        get() {
            if (_name == null) {
                Thread.sleep(2_000L)
                this._name = "홍길동"
            }
            return this._name!!
        }
}
