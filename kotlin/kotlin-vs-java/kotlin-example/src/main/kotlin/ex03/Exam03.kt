package ex03

/**
 * 가변 불변 컬렉션
 */
fun main() {

    // 가변
    val list1 = mutableListOf<User>()
    list1.add(User("a", 10));
    list1.add(User("b", 20));
    list1.add(User("c", 30));

    // 불변
    val list2 = listOf<User>(
        User("d", 10),
        User("e", 20),
        User("f", 30)
    )

    for (user in list1) {
        println(user)
    }

    for ((index, user) in list1.withIndex()) {
        println("Index: $index, user: $user")
    }

    list2.forEach { it -> println(it) }

    list2.forEachIndexed { index, user ->
        println("Index: $index, user: $user")
    }

    list2.forEachIndexed(fun (index, user) {
        println("Index: $index, user: $user")
    })
}

class User(
    var name: String,
    val age: Int
) {
    override fun toString(): String {
        return "User(name='$name', age=$age)"
    }
}