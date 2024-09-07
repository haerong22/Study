class MyGeneric<T>(val t: T) {

}

class MyGeneric2<out T>(val t: T) {}

class Bag<T> {

    fun saveAll(
        to: MutableList<in T>,
        from: MutableList<T>,
    ) {
        to.addAll(from)
    }
}

fun main() {

    // 제네릭을 사용한 클래스의 인스턴스를 만드려면 타입 아규먼트 제공
    val generic1 = MyGeneric<String>("테스트")

    // 생략 가능
    val generic2 = MyGeneric("테스트")

    // 변수의 타입에 제네릭을 사용한 경우
    val list1: MutableList<String> = mutableListOf()

    // 타입아규먼트를 생성자에서 추가
    val list2 = mutableListOf<String>()

    val list3: List<*> = listOf("테스트")
    val list4: List<*> = listOf(1, 2, 3, 4)

    // PECS(Producer-Extends, Consumer-Super)
    // 공변성은 자바 제네릭의 extends 코틀린에서는 out
    val myGeneric = MyGeneric2("테스트")
    val charGeneric: MyGeneric2<CharSequence> = myGeneric

    // 반공변성 자바 제네릭의 super 코틀린에서는 in
    val bag = Bag<String>()

    val to = mutableListOf<CharSequence>("1", "2")
    val from = mutableListOf<String>("3", "4")
    bag.saveAll(to, from)

    println(to)
}