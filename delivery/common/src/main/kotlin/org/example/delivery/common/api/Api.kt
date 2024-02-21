package org.example.delivery.common.api

import jakarta.validation.Valid
import org.example.delivery.common.error.ErrorCode

data class Api<T>(
    var result: Result? = null,

    @field:Valid
    val body: T? = null,
) {

    companion object {

        @JvmStatic
        fun <T> ok(body: T?): Api<T> {
            return Api(
                result = Result.ok(),
                body = body
            )
        }

        @JvmStatic
        fun <T> error(
            errorCode: ErrorCode,
        ): Api<T> {
            return error(errorCode, "에러")
        }

        @JvmStatic
        fun <T> error(
            errorCode: ErrorCode,
            tx: Throwable
        ): Api<T> {
            return error(errorCode, tx)
        }

        @JvmStatic
        fun <T> error(
            errorCode: ErrorCode,
            description: String,
        ): Api<T> {
            return Api(
                result = Result.error(errorCode, description)
            )
        }
    }
}