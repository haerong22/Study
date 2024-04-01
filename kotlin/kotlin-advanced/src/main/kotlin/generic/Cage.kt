package generic

fun main() {
    val cage = Cage()
    cage.put(Carp(name = "잉어"))

    // 타입 캐스팅
    val carp: Carp = cage.getFirst() as Carp

    // 타입 캐스팅, 엘비스 연산
    val carp2: Carp = cage.getFirst() as? Carp ?: throw IllegalArgumentException()

    // 제네릭
    val carpCage = Cage2<Carp>()
    carpCage.put(Carp(name = "잉어"))
    val carp3: Carp = carpCage.getFirst()
}

class Cage {
    private val animals: MutableList<Animal> = mutableListOf()

    fun getFirst(): Animal {
        return this.animals.first()
    }

    fun put(animal: Animal) {
        this.animals.add(animal)
    }

    fun moveFrom(cage: Cage) {
        this.animals.addAll(cage.animals)
    }
}
