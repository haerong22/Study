package generic

/**
 * 무공변, 공변, 반공변
 *
 * 타입 파라미터의 상속관계가 제네릭 클래스에서 유지되지 않는다.(무공변)
 * 타입 파라미터의 상속관계가 제네릭 클래스에서 유지된다.(공변)
 * 타입 파라미터의 상속관계가 제네릭 클래스에서 반대로 유지된다.(반공변)
 *
 * in, out 으로 변성을 줄 수 있다.
 * - out : 함수 파라미터 입장에서의 생산자, 공변
 * - in : 함수 파라미터 입장에서의 소비자, 반공변
 */
fun main() {
    val goldFishCage = Cage2<GoldFish>()
    goldFishCage.put(GoldFish("금붕어"))

    val fishCage = Cage2<Fish>()

    fishCage.moveFrom(goldFishCage)
    goldFishCage.moveTo(fishCage)

    val fish: Fish = fishCage.getFirst()

}

/**
 * 제네릭 적용
 */
class Cage2<T> {

    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T {
        return this.animals.first()
    }

    fun put(animal: T) {
        this.animals.add(animal)
    }

    fun moveFrom(otherCage: Cage2<out T>) {
        this.animals.addAll(otherCage.animals)
    }

    fun moveTo(otherCage: Cage2<in T>) {
        otherCage.animals.addAll(this.animals)
    }
}