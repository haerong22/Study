package typesafe

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.cast

abstract class Animal(
    val name: String,
)

abstract class Fish(name: String) : Animal(name)

class GoldFish(name: String) : Fish(name)

class Carp(name: String) : Fish(name)

class TypeSafeCage {
    private val animals = mutableMapOf<KClass<*>, Animal>()

    fun <T : Animal> getOne(type: KClass<T>): T {
        return type.cast(animals[type])
    }

    fun <T : Animal> putOne(
        type: KClass<T>,
        animal: T,
    ) {
        animals[type] = type.cast(animal)
    }

    inline fun <reified T : Animal> getOne(): T {
        return this.getOne(T::class)
    }

    inline fun <reified T : Animal> putOne(animal: T) {
        this.putOne(T::class, animal)
    }
}

class SuperTypeSafeCage {
    private val animals = mutableMapOf<SuperTypeToken<*>, Any>()

    fun <T : Any> getOne(token: SuperTypeToken<T>): T {
        return this.animals[token] as T
    }

    fun <T : Any> putOne(
        token: SuperTypeToken<T>,
        animal: T,
    ) {
        animals[token] = animal
    }
}

// SuperTypeToken를 구현한 클래스가 인스턴스화 되면 T 정보를 내부 변수에 저장한다.
abstract class SuperTypeToken<T> {
    val type: KType = this::class.supertypes[0].arguments[0].type!!

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as SuperTypeToken<*>

        return type == other.type
    }

    override fun hashCode(): Int {
        return type.hashCode()
    }
}

fun main() {
    val typeSafeCage = TypeSafeCage()
    typeSafeCage.putOne(Carp("잉어"))
    val carp = typeSafeCage.getOne<Carp>()

    val token1 = object : SuperTypeToken<List<GoldFish>>() {}
    val token2 = object : SuperTypeToken<List<GoldFish>>() {}
    val token3 = object : SuperTypeToken<List<Carp>>() {}

    println(token1.equals(token2))
    println(token1.equals(token3))

    val superTypeSafeCage = SuperTypeSafeCage()
    superTypeSafeCage.putOne(token1, listOf(GoldFish("금붕어1"), GoldFish("금붕어2")))
    val result = superTypeSafeCage.getOne(token1)
    println(result)
}
