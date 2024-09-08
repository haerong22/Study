/**
 * f(1,3) = 1 + 3 = 4
 */
//data class Tuple(val a: Int, val b: Int)
//fun plus(tuple: Tuple) = tuple.a + tuple.b
fun plus(pair: Pair<Int, Int>) = pair.first + pair.second

fun main() {

    val plus = plus(Pair(1, 3))
    println(plus)

    println("========================")

    val pair = Pair("A", 1)
    val newPair = pair.copy(first = "B")
    println(newPair)

    println("========================")

    val second = newPair.component2()
    println(second)

    println("========================")

    val list = newPair.toList()
    println(list)

    println("========================")

    val triple = Triple("A", "B", "C")
    println(triple)

    println("========================")

    val (a, b, c) = triple
    println("$a, $b, $c")

    println("========================")

    val list2 = triple.toList()
    val (a1, a2, a3) = list2
    println("$a1, $a2, $a3")

    println("========================")

    val map = mutableMapOf("key" to "value")
    for ((key, value) in map) {
        println("$key -> $value")
    }
}