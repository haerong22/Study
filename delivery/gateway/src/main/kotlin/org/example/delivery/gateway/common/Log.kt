package org.example.delivery.gateway.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface Log {

    val log: Logger get() = LoggerFactory.getLogger(this.javaClass)
}