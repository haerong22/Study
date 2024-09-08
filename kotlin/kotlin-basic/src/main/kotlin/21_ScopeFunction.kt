/**
 * 함수명       수신자 객체 참조 방법    반환 값            확장함수 여부
 * let        it                  함수의 결과              O
 * run        this                함수의 결과              O
 * with       this                함수의 결과              X
 * apply      this                컨텍스트 객체             O
 * also       it                  컨텍스트 객체             O
 */

fun let() {
    val str: String? = "hello"

    val result: String? = str?.let {
        println(it)

        val abc: String? = "abc"
        abc?.let {

            val def: String? = "def"
            def?.let {
                println("abcdef")
            }
        }
        "not null"
    }
        ?: "null"

    println(result)
}

class DatabaseClient {
    var url: String? = null
    var username: String? = null
    var password: String? = null

    fun connect(): Boolean {
        println("DB 접속 중...")
        Thread.sleep(1000)
        println("DB 접속 완료")
        return true
    }
}

fun run() {

    val connected = DatabaseClient().run {
        url = "localhost:3306"
        username = "root"
        password = "1234"
        connect()
    }

    println(connected)
}

fun with() {

    val str = "안녕하세요"

    val length = with(str) {
        length
    }
    println(length)
}

fun apply() {
    val client = DatabaseClient().apply {
        url = "localhost:3306"
        username = "root"
        password = "1234"
    }

    val connected = client.connect()
    println(connected)
}

class User(val name: String, val password: String) {

    fun validate() {
        if (name.isNotEmpty() && password.isNotEmpty()) {
            println("검증 성공")
        } else {
            println("검증 실패")
        }
    }

    fun printName() = println(name)
}

fun also() {

    User("name", "1234").also {
        it.validate()
        it.printName()
    }
}

fun main() {
    let()

    println("=====================")

    run()

    println("=====================")

    with()

    println("=====================")

    apply()

    println("=====================")

    also()
}