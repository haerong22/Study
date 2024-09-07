sealed class Developers {

    abstract val name: String
    abstract fun code(language: String)
}

data class BackendDeveloper(override val name: String) : Developers() {
    override fun code(language: String) {
        println("$language Backend developer")
    }
}

data class FrontendDeveloper(override val name: String) : Developers() {
    override fun code(language: String) {
        println("$language Frontend developer")
    }
}

data class AndroidDeveloper(override val name: String) : Developers() {
    override fun code(language: String) {
        println("$language Android developer")
    }
}

data object OtherDevelopers : Developers() {
    override val name = "익명"

    override fun code(language: String) {
        TODO("Not yet implemented")
    }
}

object DeveloperPool {
    val pool = mutableMapOf<String, Developers>()

    fun add(developer: Developers) = when (developer) {
        is BackendDeveloper -> pool[developer.name] = developer
        is FrontendDeveloper -> pool[developer.name] = developer
        is AndroidDeveloper -> pool[developer.name] = developer
        is OtherDevelopers -> println("지원하지 않음")
    }

    fun get(name: String) = pool[name]
}

fun main() {
    val backendDeveloper = BackendDeveloper("홍길동")
    DeveloperPool.add(backendDeveloper)

    val frontendDeveloper = FrontendDeveloper("철수")
    DeveloperPool.add(frontendDeveloper)

    println(DeveloperPool.get("홍길동"))
    println(DeveloperPool.get("철수"))
}