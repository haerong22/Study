package initialize

import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * DelegateProperty, DelegateProvider
 */
class Person12() {
    val name: String by DelegateProvider("홍길동")
    val country: String by DelegateProvider("한국")
}

class DelegateProvider(
    private val initValue: String
) : PropertyDelegateProvider<Any, DelegateProperty> {
    override fun provideDelegate(thisRef: Any, property: KProperty<*>) : DelegateProperty {
        if (property.name != "name") {
            throw IllegalArgumentException("${property.name} : name 만 연결 가능합니다!")
        }
        return DelegateProperty(initValue);
    }
}

class DelegateProperty(
    private val initValue: String,
) : ReadOnlyProperty<Any, String> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return initValue
    }
}

fun main() {
    val p = Person12()
    println(p.name)
    println(p.country)
}
