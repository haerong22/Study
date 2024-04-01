package reflection

import kotlin.reflect.KClass
import kotlin.reflect.cast
import kotlin.reflect.full.createType

class KReflection

class GoldFish(val name: String) {
    fun print() {
        println("금붕어 이름은 $name 입니다.")
    }
}

fun castToGoldFish(obj: Any): GoldFish {
//    return obj as GoldFish
    return GoldFish::class.cast(obj)
}

fun main() {
    val kClass1: KClass<Reflection> = Reflection::class

    val ref = Reflection()
    val kClass2: KClass<out Reflection> = ref::class

    val kClass3: KClass<out Any> = Class.forName("reflection.Reflection").kotlin

    val kType = GoldFish::class.createType()

    val goldFish = GoldFish("금붕")
    goldFish::class.members.first { it.name == "print" }.call(goldFish)
}
