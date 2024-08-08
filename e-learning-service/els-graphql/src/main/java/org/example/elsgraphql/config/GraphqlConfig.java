package org.example.elsgraphql.config;

import graphql.scalars.ExtendedScalars;
import org.example.elsgraphql.directive.AuthenticationDirective;
import org.example.elsgraphql.directive.AuthorizationDirective;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphqlConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer(
            AuthenticationDirective authenticationDirective,
            AuthorizationDirective authorizationDirective
    ) {
        return wiringBuilder -> wiringBuilder
                .scalar(ExtendedScalars.Date)
                .scalar(ExtendedScalars.DateTime)
                .scalar(ExtendedScalars.GraphQLLong)
                .directive("authenticate", authenticationDirective)
                .directive("authorize", authorizationDirective)
                ;
    }
}
