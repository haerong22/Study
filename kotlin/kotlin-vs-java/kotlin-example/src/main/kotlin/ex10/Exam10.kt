package ex10

/**
 * 확장함수
 */
fun main() {

    val user = ExamUser(name = "abcd")
    exam10(user)
}

fun exam10(examUser: ExamUser?) {

    examUser?.let {

        if (it.name.isNotNullOrBlank()) {
            println(it.name)
        }
    }

    if (examUser.isNotNull() && examUser?.name.isNotNullOrBlank()) {
        println(examUser?.name)
    }

}

data class ExamUser(
    var name: String? = null
)

// 확장 함수
fun String?.isNotNullOrBlank(): Boolean {
    return !this.isNullOrBlank()
}

fun Any?.isNotNull(): Boolean {
    return this != null
}