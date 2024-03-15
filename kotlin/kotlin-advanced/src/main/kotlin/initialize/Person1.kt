package initialize

class Person1(
    private val name: String,
) {

    val isKim: Boolean
        get() = this.name.startsWith("김")

    val maskingName: String
        get() = name[0] + (1..<name.length).joinToString("") { "*" }
}