/**
 * Data class
 *
 * - getter(), (setter())
 * - equals()
 * - hashCode()
 * - toString()
 * - copy()
 * - componentN()
 */
data class Person(
    val name: String,
    val age: Int,
)

fun main() {

    val person1 = Person("name", 10)
    val person2 = Person("name", 10)

    println(person1 == person2) // true

    val set = hashSetOf(person1)
    println(set.contains(person2)) // true

    println(person1)

    val person3 = person1.copy(name = "name2")
    println(person3)

    println("name: ${person1.component1()}, age: ${person1.component2()}")

    val (name, age) = person1

    println("name: $name, age: $age")
}