
fun getNullStr(): String? = null

fun getLengthIfNotNull(str: String?) = str?.length ?: 0

fun main() {

//    val nullableStr = getNullStr()
//
//    val nullableStrLength = nullableStr?.length ?: 0
//    println(nullableStrLength)
//
//    val length = getLengthIfNotNull(null)
//    println(length)

//    val a: String = null
//    var b: String = "asdf"
//    b = null

//    val a: String? = null
//    println(a?.length)
//
//    val b: Int = if (a != null) a.length else 0
//    println(b)
//
//    // 엘비스 연산자
//    val c = a?.length ?: 0
//    println(c)

//    throw NullPointerException()

    val c: String? = null
    val d = c!!.length
}
