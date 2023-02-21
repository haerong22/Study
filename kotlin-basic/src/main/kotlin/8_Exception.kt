import java.lang.RuntimeException

fun main() {

//    Thread.sleep(1) // (O)
//
//    try {
//        throw RuntimeException()
//    } catch (e: Exception) {
//        println("에러 발생시 수행")
//    } finally {
//        println("finally 실행")
//    }
//
//    val a = try {
//        "test".length
//    } catch (e: Exception) {
//        println("에러 발생시 수행")
//    }
//
//    println(a)


    val a: String? = null
    val b: String = a ?: fail("a is null")

    println(b.length)
}

fun fail(message: String): Nothing {
    throw IllegalArgumentException(message);
}