/**
 * Lazy init
 */
class HelloBot {

    val greeting: String by lazy { // 한 번만 호출
        println("초기화 로직 수행")
        getHello()
    }

    fun sayHello() = println(greeting)
}

fun getHello() = "안녕하세요"

/**
 * Late init
 */
class LateInit {

    lateinit var text: String

    val textInitialized: Boolean
        get() = ::text.isInitialized

    fun printText() {
        if (this::text.isInitialized) {
            println("초기화됨")
        } else {
            text = "Hello"
        }
        println(text)
    }
}

fun main() {
    val hello = HelloBot()

    for (i in 1..5) {
        Thread {
            hello.sayHello()
        }.start()
    }

    val test = LateInit()
    println(test.textInitialized)
    test.printText()
    println(test.textInitialized)

}