import DateTimeUtils.same
import java.time.LocalDateTime

object Singleton {

    val a = 1234

    fun printA() = println(a)

}

object DateTimeUtils {

    val now: LocalDateTime
        get() = LocalDateTime.now()

    const val DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

    fun same(a: LocalDateTime, b: LocalDateTime) = a == b
}

class MyClass private constructor() {

    companion object {
        val a = 1234

        fun newInstance() = MyClass()
    }
}

fun main() {

    println(Singleton.a)
    Singleton.printA()

    println("=======================")

    println(DateTimeUtils.now)
    println(DateTimeUtils.now)

    println(DateTimeUtils.DEFAULT_DATE_TIME_FORMAT)

    val now = DateTimeUtils.now
    println(DateTimeUtils.same(now, now))

    println("=======================")

    println(MyClass.a)
    println(MyClass.newInstance())

    println(MyClass.Companion.a)
    println(MyClass.Companion.newInstance())
}