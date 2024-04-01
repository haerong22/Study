package initialize

import kotlin.reflect.KProperty

/**
 * 인스턴스화 시점과 변수 초기화 시점 분리
 *
 * 위임 프로퍼티( lazy 원리 )
 *
 * -> 초기화 과정이 비싼 작업인 경우 초기화를 한번만 실행
 *
 */
class Person7 {
    private val delegateProperty by LazyInitProperty {
        Thread.sleep(2_000L)
        "홍길동"
    }
}

class LazyInitProperty<T>(val init: () -> T) {
    private var _value: T? = null
    private val value: T
        get() {
            if (_value == null) {
                this._value = init()
            }
            return _value!!
        }

    operator fun getValue(
        thisRef: Any,
        property: KProperty<*>,
    ): T {
        return value
    }
}
