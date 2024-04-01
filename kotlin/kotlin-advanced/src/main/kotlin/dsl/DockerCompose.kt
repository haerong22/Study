package dsl

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main() {
    val yml =
        dockerCompose {
            version { 3 }
            service(name = "db") {
                image { "mysql" }
                env("USER" - "myuser")
                env("PASSWORD" - "mypassword")
                port(host = 9999, container = 3306)
            }
        }

    println(yml.render("  "))
}

fun dockerCompose(init: DockerCompose.() -> Unit): DockerCompose {
    val dockerCompose = DockerCompose()
    dockerCompose.init()
    return dockerCompose
}

@YamlDsl
class DockerCompose {
    private var version: Int by onceNotNull()
    private val services = mutableListOf<Service>()

    fun version(init: () -> Int) {
        version = init()
    }

    fun service(
        name: String,
        init: Service.() -> Unit,
    ) {
        val service = Service(name)
        service.init()
        services.add(service)
    }

    fun render(indent: String): String {
        return StringBuilder().apply {
            appendNew("version: '$version'")
            appendNew("services:")
            appendNew(services.joinToString("\n") { it.render(indent) }.addIndent(indent, 1))
        }.toString()
    }
}

@YamlDsl
class Service(val name: String) {
    private var image: String by onceNotNull()
    private val environments = mutableListOf<Environment>()
    private val portRule = mutableListOf<PortRule>()

    fun image(init: () -> String) {
        image = init()
    }

    fun env(environment: Environment) {
        environments.add(environment)
    }

    fun port(
        host: Int,
        container: Int,
    ) {
        this.portRule.add(PortRule(host = host, container = container))
    }

    fun render(indent: String): String {
        return StringBuilder().apply {
            appendNew("$name:")
            appendNew(
                StringBuilder().apply {
                    appendNew("image: $image")
                    appendNew("environment:")
                    environments
                        .joinToString("\n") { "- ${it.key}: ${it.value}" }
                        .addIndent(indent, 1)
                        .also { appendNew(it) }
                    appendNew("port:")
                    portRule
                        .joinToString("\n") { "- \"${it.host}:${it.container}\"" }
                        .addIndent(indent, 1)
                        .also { appendNew(it) }
                }.toString().addIndent(indent, 1),
            )
        }.toString()
    }
}

data class Environment(
    val key: String,
    val value: String,
)

data class PortRule(
    val host: Int,
    val container: Int,
)

operator fun String.minus(other: String): Environment {
    return Environment(
        key = this,
        value = other,
    )
}

fun StringBuilder.appendNew(
    str: String,
    indent: String = "",
    times: Int = 0,
) {
    (1..times).forEach { _ -> this.append(indent) }
    this.append(str)
    this.append("\n")
}

fun String.addIndent(
    intent: String,
    times: Int = 0,
): String {
    val allIndent = (1..times).joinToString("") { intent }
    return this
        .split("\n")
        .joinToString("\n") { "$allIndent$it" }
}

fun <T> onceNotNull() =
    object : ReadWriteProperty<Any?, T> {
        private var value: T? = null

        override fun getValue(
            thisRef: Any?,
            property: KProperty<*>,
        ): T {
            if (this.value == null) {
                throw IllegalArgumentException("변수가 초기화 되지 않았습니다.")
            }
            return this.value!!
        }

        override fun setValue(
            thisRef: Any?,
            property: KProperty<*>,
            value: T,
        ) {
            if (this.value != null) {
                throw IllegalArgumentException("이 변수는 한번만 초기화가 가능합니다.")
            }
            this.value = value
        }
    }

@DslMarker
annotation class YamlDsl
