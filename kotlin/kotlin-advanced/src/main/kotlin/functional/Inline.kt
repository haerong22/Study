package functional

class Inline {
}

fun main() {
    repeat(2) { println("Hello World!") }

    iterate2(listOf(1, 2, 3, 4, 5)) { num ->
        if(num ==3) {
            return // inline 함수로 사용 가능(단, main 함수를 return 하게 된다!)
        }
        println(num)
    }
}

inline fun iterate2(numbers: List<Int>, exec: (Int) -> Unit) {
    for (number in numbers) {
        exec(number)
    }
}

inline fun repeat(
    times: Int,
    exec: () -> Unit,
//    noinline exec: () -> Unit, // noinline 키워드를 사용해 인라인을 막을 수 있다.
) {
    for (i in 1..times) {
        exec()
    }
}