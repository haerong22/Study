fun String.first(): Char {
    return this[0]
}

fun String.addFirst(char: Char): String {
    return char + this
}

class MyExample {
    fun printMessage() = println("클래스")
}

// 클래스 함수가 더 우선
fun MyExample.printMessage() = println("확장함수")

// Nullable
fun MyExample?.printNullOrNotNull() {
    if (this == null) println("Null")
    else println("Not Null")
}

fun main() {

    println("abcd".first())
    println("abcd".addFirst('z'))

    println("=======================")

    MyExample().printMessage() // 클래스

    println("=======================")

    var myExample: MyExample? = null
    myExample.printNullOrNotNull()

    myExample = MyExample()
    myExample.printNullOrNotNull()
}