package generic

/**
 * 타입 파라미터가 소비만 한다.
 * -> 타입 파라미터가 반환 타입에 사용되지 않는다.
 * -> 클래스를 반공변하게 만들 수 있다.(선언 지점 변성)
 */
class Cage4<in T> {
    private val animals: MutableList<T> = mutableListOf()

    fun put(animal: T) {
        this.animals.add(animal)
    }

    fun putAll(animals: List<T>) {
        this.animals.addAll(animals)
    }
}
