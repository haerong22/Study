package dicontainer

import kotlin.reflect.KClass

object ContainerV1 {
    // 등록한 클래스를 보관
    private val registeredClasses = mutableSetOf<KClass<*>>()

    fun register(clazz: KClass<*>) {
        registeredClasses.add(clazz)
    }

    fun <T : Any> getInstance(type: KClass<T>): T {
        return registeredClasses.firstOrNull { clazz -> clazz == type }
            ?.let { clazz -> clazz.constructors.first().call() as T }
            ?: throw IllegalArgumentException("해당 인스턴스 타입을 찾을 수 없습니다.")
    }
}

fun main() {
    ContainerV1.register(AService::class)
    val aService = ContainerV1.getInstance(AService::class)
    aService.print()
}

class AService {

    fun print() {
        println("AService 입니다.")
    }
}
