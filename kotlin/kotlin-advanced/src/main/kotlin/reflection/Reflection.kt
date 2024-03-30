package reflection

import kotlin.reflect.KFunction
import kotlin.reflect.full.createType
import kotlin.reflect.full.hasAnnotation

/**
 * Example
 *
 * 함수 executeAll(obj: Any) 를 만든다
 * obj가 @Executable 어노테이션을 가지고 있으면,
 * obj에서 파라미터가 없고 반환 타입이 Unit인 함수를 모두 실행한다.
 */
fun main() {
    executeAll(Reflection())
}

annotation class Executable

@Executable
class Reflection {

    fun a() {
        println("a 입니다.")
    }

    fun b(n: Int) {
        println("b 입니다.")
    }

}

fun executeAll(obj: Any) {
    val kClass = obj::class
    if (!kClass.hasAnnotation<Executable>()) return

    val callableFunctions = kClass.members.filterIsInstance<KFunction<*>>()
        .filter { it.returnType == Unit::class.createType() }
        .filter { it.parameters.size == 1 && it.parameters[0].type == kClass.createType() }

    callableFunctions.forEach { func -> func.call(obj) }
}