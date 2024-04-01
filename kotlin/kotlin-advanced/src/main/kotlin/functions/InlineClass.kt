package functions

import java.awt.print.Book

class InlineClass

fun main() {
    val key = Key("비밀 번호")
    println(key)

    val userId = 1L
    val bookId = 2L
    handle(bookId = bookId, userId = userId)

    val inlineUserId = Id<User>(1L)
    val inlineBookId = Id<Book>(2L)
    handleV2(inlineUserId, inlineBookId)
}

@JvmInline
value class Key(val key: String)

class User(
    val id: Id<User>,
    val name: String,
)

class Author(
    val id: Id<Book>,
    val author: String,
)

fun handle(
    userId: Long,
    bookId: Long,
) {
}

fun handleV2(
    userId: Id<User>,
    bookId: Id<Book>,
) {
}

@JvmInline
value class Id<T>(val Id: Long)

@JvmInline
value class Number(val num: Long) {
    init {
        require(num in 1..10)
    }
}
