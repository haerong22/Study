package functional

class Calculator {
}

fun main() {
    println(calculator(5, 3, Operator.PLUS))
}

fun calculator(num1: Int, num2: Int, oper: Operator) = oper.calcFun(num1, num2)

enum class Operator(
    private val oper: Char,
    val calcFun: (Int, Int) -> Int
) {
    PLUS('+', { a, b -> a + b }),
    MINUS('-', { a, b -> a - b }),
    MULTIPLY('*', { a, b -> a * b }),
    DIVIDE('/', { a, b ->
        if (b == 0) {
            throw IllegalArgumentException("0으로 나눌 수 없습니다.")
        } else {
            a / b
        }
    }),
}