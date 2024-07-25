package org.example.apigateway.global.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final ReactiveUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .flatMap(auth -> {
                    String username = auth.getName();
                    String password = auth.getCredentials().toString();

                    return userDetailsService.findByUsername(username)
                            .filter(userDetails -> passwordEncoder.matches(password, userDetails.getPassword()))
                            .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails, null))
                            .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid Credentials")));
                });
    }
}
