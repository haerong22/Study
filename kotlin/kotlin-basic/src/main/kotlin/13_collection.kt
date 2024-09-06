import java.util.LinkedList

/**
 * Collection
 *
 * - List, Set, Map
 * - 종류
 *   - 불변 컬렉션(immutable)
 *   - 가변 컬랙션(mutable)
 */
fun main() {

    // immutable list
    val currencyList = listOf("달러", "유로", "원")

    // mutable list
    val mutableCurrencyList = mutableListOf("달러", "유로").apply {
        add("원")
    }

    // immutable set
    val numberSet = setOf(1, 2, 3)

    // mutable set
    val mutableNumberSet = mutableSetOf(1, 2, 3).apply {
        add(1)
    }

    // immutable map
    val numberMap = mapOf("one" to 1, "two" to 2, "three" to 3)

    // mutable map
    val mutableNumberMap = mutableMapOf<String, Int>().apply {
        this["one"] = 1
        this["two"] = 2
        this["three"] = 3
    }

    // Collection Builder
    // - builder 내부에서는 mutable list 를 사용하여 immutable list 반환
    val numberList = buildList {
        add(1)
        add(2)
        add(3)
    }

    // linked list
    val linkedList = LinkedList<Int>().apply {
        addFirst(1)
        add(2)
        addLast(3)
    }

    // array list
    val arrayList = ArrayList<Int>().apply {
        add(1)
        add(2)
        add(3)
    }

    /*
        loop
     */
    val iterator = currencyList.iterator()
    while (iterator.hasNext()) {
        println(iterator.next())
    }

    println("===========================")

    for (currency in currencyList) {
        println(currency)
    }

    println("===========================")

    currencyList.forEach {
        println(it)
    }

    println("===========================")

    // for loop -> map
    val lowerList = listOf("a", "b", "c")
    val upperList = lowerList.map { it.uppercase() }
    println(upperList)

    // kotlin inline function != java stream
    val filteredList = upperList.filter { it == "A" || it == "C" }
    println(filteredList)

    // java stream ~= asSequence
    val asSequenceList = upperList
        .asSequence()
        .filter { it == "A" || it == "C" }
        .toList()

    println(asSequenceList)
}