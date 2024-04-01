package functional

/**
 * 함숫값 / 함수 리터럴
 * - 일반 함수와 달리 변수로 간주하거나 파라미터에 넣을 수 있는 함수
 * - 함수 리터럴을 표현하는 방법 -> 람다식, 익명함수
 *
 * 람다
 * - 이름이 없는 함수
 */
fun main() {
    // 람다식
    compute(5, 3) { a, b -> a + b }

    // 익명 함수
    compute(
        5,
        3,
        fun(
            a: Int,
            b: Int,
        ) = a + b,
    )

    // 람다식
    iterate(listOf(1, 2, 3, 4, 5)) { num ->
        if (num != 3) {
            println(num)
        }
    }

    // 익명 함수
    iterate(
        listOf(1, 2, 3, 4, 5),
        fun (num) {
            if (num == 3) {
                return
            }
            println(num)
        },
    )
}

fun compute(
    num1: Int,
    num2: Int,
    op: (Int, Int) -> Int,
): Int {
    return op(num1, num2)
}

fun iterate(
    numbers: List<Int>,
    exec: (Int) -> Unit,
) {
    for (number in numbers) {
        exec(number)
    }
}
