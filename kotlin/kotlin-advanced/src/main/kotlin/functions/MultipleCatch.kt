package functions

import kotlin.reflect.KClass

class MultipleCatch

fun logic(n: Int) {
    when {
        n > 0 -> throw AException()
        n == 0 -> throw BException()
    }

    throw CException()
}

class AException : RuntimeException()

class BException : RuntimeException()

class CException : RuntimeException()

fun main() {
    try {
    } catch (e: Exception) {
        when (e) {
            is AException,
            is BException,
            -> TODO()
            is CException -> TODO()
        }

        throw e
    }

    runCatching { logic(10) }
        .onError(AException::class, BException::class) {
            println("A 예외 B 예외")
        }
        .onError(AException::class) {
        }
}

class ResultWrapper<T>(
    private val result: Result<T>,
    private val knownExceptions: MutableList<KClass<out Throwable>>,
) {
    fun toResult(): Result<T> {
        return this.result
    }

    fun onError(
        vararg exceptions: KClass<out Throwable>,
        action: (Throwable) -> Unit,
    ): ResultWrapper<T> {
        this.result.exceptionOrNull()?.let {
            if (it::class in exceptions && it::class !in this.knownExceptions) {
                action(it)
            }
        }
        return this
    }
}

fun <T> Result<T>.onError(
    vararg exceptions: KClass<out Throwable>,
    action: (Throwable) -> Unit,
): ResultWrapper<T> {
    exceptionOrNull()?.let {
        if (it::class in exceptions) {
            action(it)
        }
    }

    return ResultWrapper(this, exceptions.toMutableList())
}
