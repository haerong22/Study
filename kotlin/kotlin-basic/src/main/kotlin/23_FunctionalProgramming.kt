val printHello: () -> Unit = { println("Hello") }

val func: () -> Unit = {}

fun call(block: () -> Unit) {
    block()
}

val printMessage1: (String) -> Unit = { message: String -> println(message) }
val printMessage2: (String) -> Unit = { message -> println(message) }
val printMessage3: (String) -> Unit = { println(it) }

val plus: (Int, Int) -> Int = { a, b -> a + b }

fun forEachStr(collection: Collection<String>, action: (String) -> Unit) {
    for (item in collection) {
        action(item)
    }
}

fun outerFunc(): () -> Unit {
    return {
        println("익명 함수!")
    }
}

// 람다 표현식
val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
val sum2 = { x: Int, y: Int -> x + y }

fun main() {

    val list = mutableListOf(printHello)

    list[0]()

    val func = list[0]
    func()

    call(printHello)

    println("====================")

    printMessage1("Hello")
    printMessage2("Hello")
    printMessage3("Hello")

    println("====================")

    println(plus(1, 2))

    println("====================")

    val strList = listOf("a", "b", "c")
    val printStr: (String) -> Unit = { println(it) }
    forEachStr(strList, printStr)

    val uppercase: (String) -> String = { it.uppercase() }
    println(strList.map(uppercase))

    println("====================")

    outerFunc()()
    val func2 = outerFunc()
    func2()

    println("====================")

    val strList2 = listOf("a", "b", "c")
    forEachStr(strList2) { println(it) }

    println("====================")

    val callReference = ::printHello
    callReference()()

    println("====================")

    val numberList = listOf("1", "2", "3")
    numberList.map { it.toInt() }.forEach { println(it) }
    numberList.map(String::toInt).forEach(::println)
}
