package org.example.delivery.common.error

interface ErrorCode {

    fun getHttpStatusCode(): Int
    fun getErrorCode(): Int
    fun getDescription(): String
}