
fun main() {

    // if...else
    val age: Int = 20

    if (age < 20) {
        println("학생입니다.")
    } else {
        println("성인입니다.")
    }

    // 코틀린의 if...else 는 값을 리턴할 수 있다.

    val score: Int = 80

    val message = if (score >= 80) {
        "합격"
    } else {
        "불합격"
    }

    println(message)
}
