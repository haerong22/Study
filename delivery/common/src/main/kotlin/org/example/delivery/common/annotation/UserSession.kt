package org.example.delivery.common.annotation

import org.springframework.stereotype.Service

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Service
annotation class UserSession(
)
