package dicontainer

import org.reflections.Reflections
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.cast

class DI

fun start(clazz: KClass<*>) {
    val reflections = Reflections(clazz.packageName)
    val classes = reflections.getTypesAnnotatedWith(MyService::class.java)
    classes.forEach { c -> ContainerV3.register(c.kotlin) }
}

private val KClass<*>.packageName: String
    get() {
        val name =
            this.qualifiedName
                ?: throw IllegalArgumentException("익명 객체입니다!")
        val hierarchy = name.split(".")
        return hierarchy.subList(0, hierarchy.lastIndex).joinToString(".")
    }

object ContainerV3 {
    // 등록한 클래스를 보관
    private val registeredClasses = mutableSetOf<KClass<*>>()
    private val cachedInstances = mutableMapOf<KClass<*>, Any>()

    fun register(clazz: KClass<*>) {
        registeredClasses.add(clazz)
    }

    fun <T : Any> getInstance(type: KClass<T>): T {
        if (type in cachedInstances) {
            return type.cast(cachedInstances[type])
        }

        val instance =
            registeredClasses.firstOrNull { clazz -> clazz == type }
                ?.let { clazz -> instantiate(clazz) as T }
                ?: throw IllegalArgumentException("해당 인스턴스 타입을 찾을 수 없습니다.")

        cachedInstances[type] = instance
        return instance
    }

    private fun <T : Any> instantiate(clazz: KClass<T>): T {
        val constructor = findUsableConstructor(clazz)
        val params =
            constructor.parameters
                .map { param -> getInstance(param.type.classifier as KClass<*>) }
                .toTypedArray()

        return constructor.call(*params)
    }

    private fun <T : Any> findUsableConstructor(clazz: KClass<T>): KFunction<T> {
        return clazz.constructors.firstOrNull { constructor -> constructor.parameters.isAllRegistered }
            ?: throw IllegalArgumentException("사용할 수 있는 생성자가 없습니다.")
    }

    private val List<KParameter>.isAllRegistered: Boolean
        get() = this.all { it.type.classifier in registeredClasses }
}

fun main() {
    start(DI::class)

    val bServiceV3 = ContainerV3.getInstance(BServiceV3::class)

    bServiceV3.print()
}

annotation class MyService

@MyService
class AServiceV3 {
    fun print() {
        println("AServiceV3 입니다.")
    }
}

@MyService
class BServiceV3(
    private val aServiceV3: AServiceV3,
    private val cServiceV3: CServiceV3?,
) {
    constructor(aServiceV3: AServiceV3) : this(aServiceV3, null)

    fun print() {
        this.aServiceV3.print()
    }
}

@MyService
class CServiceV3
