package org.example.goopang.config

import graphql.scalars.ExtendedScalars
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer

@Configuration
class GraphQLConfig {

    @Bean
    fun runtimeWiringConfiguration(): RuntimeWiringConfigurer {
        return RuntimeWiringConfigurer { it.scalar(ExtendedScalars.DateTime) }
    }
}