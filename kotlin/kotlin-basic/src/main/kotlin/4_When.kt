
fun main() {

    // when ... else
    val day = 2

    val result = when (day) {
        1 -> "월요일"
        2 -> "화요일"
        3 -> "수요일"
        4 -> "목요일"
        5 -> "금요일"
        6 -> "토요일"
        7 -> "일요일"
        else -> "잘못입력"
    }
    println(result)

    val day2 = Day.SATURDAY

    when(day2) {
        Day.MONDAY -> println("월요일")
        Day.TUESDAY -> println("화요일")
        Day.WEDNESDAY -> println("수요일")
        Day.THURSDAY -> println("목요일")
        Day.FRIDAY -> println("금요일")
        Day.SATURDAY -> println("토요일")
        Day.SUNDAY -> println("일요일")
    }

    val grade = "A"

    when(grade) {
        "A", "B", "C" -> println("합격")
        else -> println("불합격")
    }
}

enum class Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}
