package ex06

/**
 * 클래스
 */
fun main() {
    val dog = Dog("해피")

    println(dog.age)
    dog.age = 10
    println(dog.age)

    dog.eat()
    dog.bark()
}

interface Bark {
    fun bark()
}

abstract class Animal(
    private val name: String? = "",
    var age: Int? = 0
) : Bark {

    fun eat() {
        println("$name 식사 시작~")
    }
}

class Dog(
    private val name: String? = "",
) : Animal(name) {

    override fun bark() {
        println("멍멍")
    }

}