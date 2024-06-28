package org.example.paymentservice.common

import io.github.oshai.kotlinlogging.KotlinLogging

object Log {

    private val logger = KotlinLogging.logger {}

    fun info(context: String, message: String, data: Any? = null) {
        logger.info {
            objectMapper.writeValueAsString(
                mapOf(
                    "context" to context,
                    "message" to message,
                    "data" to data,
                )
            )
        }
    }

    fun error(context: String, message: String, throwable: Throwable? = null) {
        logger.error {
            objectMapper.writeValueAsString(
                mapOf(
                    "context" to context,
                    "message" to message,
                    "exception" to throwable,
                    "stacktrace" to throwable?.stackTrace
                )
            )
        }
    }
}