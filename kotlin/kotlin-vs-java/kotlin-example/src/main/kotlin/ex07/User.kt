package ex07

/**
 * data -> toString, equals, hashcode, ...
 */
data class User(
    var name: String,
    var email: String? = null,
    var age: Int? = 0
) {

//    override fun toString(): String {
//        return "User(name=$name, eamil=$email, age=$age)"
//    }
}