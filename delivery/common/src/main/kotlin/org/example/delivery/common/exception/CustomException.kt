package org.example.delivery.common.exception

import org.example.delivery.common.error.ErrorCode

interface CustomException {

    val errorCode: ErrorCode?
    val errorDescription: String?
}