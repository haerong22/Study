package org.example.delivery.common.api

import org.example.delivery.common.error.CommonErrorCode
import org.example.delivery.common.error.ErrorCode

data class Result(
    val resultCode: Int? = null,
    val resultMessage: String? = null,
    val resultDescription: String? = null,
) {

    companion object {

        @JvmStatic
        fun ok(): Result {

            return Result(
                resultCode = CommonErrorCode.OK.getErrorCode(),
                resultMessage = CommonErrorCode.OK.getDescription(),
                resultDescription = "성공",
            )
        }

        @JvmStatic
        fun error(
            errorCode: ErrorCode,
        ): Result {

            return error(errorCode, "에러")
        }

        @JvmStatic
        fun error(
            errorCode: ErrorCode,
            tx: Throwable,
        ): Result {

            return error(errorCode, tx.localizedMessage)
        }

        @JvmStatic
        fun error(
            errorCode: ErrorCode,
            description: String,
        ): Result {

            return Result(
                resultCode = errorCode.getErrorCode(),
                resultMessage = errorCode.getDescription(),
                resultDescription = description,
            )
        }

    }
}