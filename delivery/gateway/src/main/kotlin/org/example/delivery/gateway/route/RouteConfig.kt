package org.example.delivery.gateway.route

import org.example.delivery.gateway.filter.ServiceApiPrivateFilter
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RouteConfig(
    private val serviceApiPrivateFilter: ServiceApiPrivateFilter
) {

    @Bean
    fun gatewayRoutes(builder: RouteLocatorBuilder): RouteLocator {

        return builder.routes()
            .route { spec ->
                spec.order(-1)
                spec.path(
                    "/service-api/api/**"
                ).filters { filterSpec ->
                    filterSpec.filter(serviceApiPrivateFilter.apply(ServiceApiPrivateFilter.Config()))
                    filterSpec.rewritePath("/service-api(?<segment>/?.*)", "\${segment}")
                }.uri(
                    "http://localhost:8080"
                )
            }
            .build()
    }
}