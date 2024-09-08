import java.io.File
import java.io.FileWriter

fun use() {
    FileWriter("test.txt")
        .use {
            it.write("Hello World")
        }

    File("test.txt").delete()
}

fun getStr(): Nothing = throw Exception("예외 발생")

fun runCachingEx() {

    // 예외 발생 시 함수 실행
    val result = runCatching { getStr() }
        .getOrElse {
            println(it.message)
            "기본값"
        }

    println(result)

    // 예외 발생시 null 반환
    val result2 = runCatching { getStr() }
        .getOrNull()

    println(result2)

    // 예외 발생시 예외 반환 예외 아니면 null 반환
    val result3 = runCatching { getStr() }
        .exceptionOrNull()

    result3?.let {
        println(it.message)
    }

    // 예외 발생시 기본값 반환
    val result4 = runCatching { getStr() }
        .getOrDefault("기본 값")

    println(result4)

    // 예외 발생 시 예외 발생
    val result5 = runCatching { "성공" }
        .getOrThrow()

    println(result5)

    // 응답 타입 변경
    val result6 = runCatching { "안녕" }
        .map {
            "${it}하세요"
        }
        .getOrThrow()

    println(result6)

    // map 실행 시 예외 처리
    val result7 = runCatching { "안녕" }
        .mapCatching {
            throw Exception("예외")
        }
        .getOrDefault("기본 값")

    println(result7)

    // 실패 시 동작
    val result8 = runCatching { getStr() }
        .recover {
            println("복구 작업 실행")
            "복구"
        }
        .getOrNull()

    println(result8)

    // recover 실행 시 예외처리
    val result9 = runCatching { getStr() }
        .recoverCatching {
            throw Exception("예외")
        }
        .getOrDefault("복구 실패")

    println(result9)
}

fun main() {
    use()

    println("===============")

    runCachingEx()

    println("===============")
}
