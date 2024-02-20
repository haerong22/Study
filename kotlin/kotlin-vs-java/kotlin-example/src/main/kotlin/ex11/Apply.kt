package ex11

/**
 * apply
 */
fun main() {

    // 생성자 패턴
    val userDto = UserDto().apply {
        this.name = "홍길동"
        myUserDto()
    }

    println(userDto)

}

fun UserDto.myUserDto() {
    println(this.name)
}