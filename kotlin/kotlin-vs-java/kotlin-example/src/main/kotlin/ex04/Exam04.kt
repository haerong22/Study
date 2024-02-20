package ex04

/**
 * Map
 */
fun main() {
    // Any == Object

    // 불변
    val map = mapOf<String, Any>(
        Pair("key1", "value"),
        "key2" to 10
    )

    // 가변
    val mutableMap = mutableMapOf<String, Any>(
        "key1" to "value"
    )
    mutableMap.set("key2", 10)
    mutableMap["key3"] = "value"

    println(mutableMap["key1"])

    val hashMap = hashMapOf<String, Any>();

    val triple = Triple(first = "", second = "", third = "")
}