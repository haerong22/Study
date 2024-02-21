package org.example.delivery.common.exception

import org.example.delivery.common.error.ErrorCode
import java.lang.RuntimeException

class ApiException : RuntimeException, CustomException {

    override val errorCode: ErrorCode?
    override val errorDescription: String?

    constructor(errorCode: ErrorCode): super(errorCode.getDescription()) {
        this.errorCode = errorCode
        this.errorDescription = errorCode.getDescription()
    }

    constructor(
        errorCode: ErrorCode,
        errorDescription: String,
    ): super(errorCode.getDescription()) {
        this.errorCode = errorCode
        this.errorDescription = errorDescription
    }

    constructor(
        errorCode: ErrorCode,
        tx: Throwable,
    ): super(tx) {
        this.errorCode = errorCode
        this.errorDescription = errorCode.getDescription()
    }

    constructor(
        errorCode: ErrorCode,
        tx: Throwable,
        errorDescription: String,
    ): super(tx) {
        this.errorCode = errorCode
        this.errorDescription = errorDescription
    }
}