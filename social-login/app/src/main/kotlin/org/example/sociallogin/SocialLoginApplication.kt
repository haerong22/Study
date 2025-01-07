package org.example.sociallogin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class SocialLoginApplication

fun main(args: Array<String>) {
    runApplication<SocialLoginApplication>(*args)
}
