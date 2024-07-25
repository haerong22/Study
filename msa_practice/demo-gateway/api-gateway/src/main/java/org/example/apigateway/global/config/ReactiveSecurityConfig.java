package org.example.apigateway.global.config;

import org.example.apigateway.global.security.JwtReactiveAuthenticationManager;
import org.example.apigateway.global.security.JwtReactiveSecurityContextRepository;
import org.example.apigateway.global.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class ReactiveSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http, JwtUtil jwtUtil,
            JwtReactiveAuthenticationManager authenticationManager,
            JwtReactiveSecurityContextRepository jwtReactiveSecurityContextRepository
    ) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authorizeExchange(exchanges ->
                        exchanges
                                .pathMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                                .pathMatchers("/login").permitAll()
                                .anyExchange().authenticated()
                )
                .authenticationManager(authenticationManager)
                .securityContextRepository(jwtReactiveSecurityContextRepository)
                .build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        return new MapReactiveUserDetailsService(
                User.withUsername("user").password(passwordEncoder().encode("1234")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder().encode("1234")).roles("ADMIN").build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
