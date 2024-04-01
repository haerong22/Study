package usable

class Usable

// kdoc <Block Tag>

/**
 * 박스 클래스.
 *
 * **강조**
 * - A
 * - B
 * - C
 *
 * @param T 박스의 아이템 타입
 * @property name 박스의 이름
 * @sample usable.extra.helloWorld
 */
class Box<T>(val name: String) {
    fun add(item: T): Boolean {
        TODO()
    }
}
