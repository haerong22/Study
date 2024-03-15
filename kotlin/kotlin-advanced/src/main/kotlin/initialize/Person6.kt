package initialize

/**
 * 인스턴스화 시점과 변수 초기화 시점 분리
 *
 * by laze 사용
 *
 * -> 초기화 과정이 비싼 작업인 경우 초기화를 한번만 실행
 *
 */
class Person6 {
    val name: String by lazy {
        Thread.sleep(2_000L)
        "홍길동"
    }
}