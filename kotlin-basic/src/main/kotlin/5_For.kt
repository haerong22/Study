
fun main() {

    // 범위 연산자 .. 사용
    // 0 <= i <= 2
    for (i in 0..2) {
        println(i)
    }

    // until 사용
    // 0 <= i < 3
    for (i in 0 until 2) {
        println(i)
    }

    // step 값 만큼 증가
    for (i in 0..4 step 2) {
        println(i)
    }

    // downTo 사용
    for (i in 3 downTo 1) {
        println(i)
    }

    // 배열 반복
    val numbers = arrayOf(1, 2, 3)
    for (i in numbers) {
        println(i)
    }
}
