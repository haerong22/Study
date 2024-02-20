package ex08

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * default value
 */
fun main() {
    Exam08(Store())

    println(DateTimeUtil().localDateTimeToString())
    println(DateTimeUtil().localDateTimeToString(LocalDateTime.now()))
}

data class Store(
    var registeredAt: LocalDateTime? = null
)

class Exam08(store: Store) {

    init {
        println(toLocalDateTimeString(store.registeredAt))
        println(toLocalDateTimeString())
    }

    fun toLocalDateTimeString(
        localDateTime: LocalDateTime? = LocalDateTime.of(2022, 2, 2, 2, 2, 2)
    ): String {
        return (localDateTime ?: LocalDateTime.now()).format(DateTimeFormatter.ofPattern("yyyy MM dd"))
    }
}

class DateTimeUtil {

    private val KST_FORMAT = "yyyy년 MM월 dd일 HH시 mm분 ss초"

    fun localDateTimeToString(
        localDateTime: LocalDateTime? = LocalDateTime.now(),
        pattern: String? = KST_FORMAT
    ): String {
        return (localDateTime?: LocalDateTime.now()).format(DateTimeFormatter.ofPattern(pattern));
    }
}