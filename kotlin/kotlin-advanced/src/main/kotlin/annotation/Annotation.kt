package annotation

import kotlin.reflect.KClass

/**
 * @Retention
 * SOURCE: 어노테이션이 컴파일 때만 존재
 * BINARY: 어노테이션이 런타임 때 존재하지만 리플렉션 사용 불가
 * RUNTIME: 어노테이션이 런타임 때 존재하고 리플렉션 사용 가능(기본값)
 *
 * @Target
 * 어노테이션을 사용 할 수 있는 곳
 */
@Repeatable
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Shape(
    val texts: Array<String>,
)

@Shape(texts = ["A", "B"])
@Shape(texts = ["A", "B"])
class Annotation

fun main() {
    val clazz: KClass<Annotation> = Annotation::class
}
