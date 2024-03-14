package generic

fun main() {
    val fishCage = Cage3<Fish>()

    val animalCage: Cage3<Animal> = fishCage
}

/**
 * 타입 파라미터가 생산만 한다.
 * -> 타입 파라미터가 반환 타입에만 사용 되었다.
 * -> 클래스를 공변하게 만들 수 있다.(선언 지점 변성)
 */
class Cage3<out T> {

    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T {
        return this.animals.first()
    }

    fun getAll(): List<T> {
        return this.animals
    }
}