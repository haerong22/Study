package org.example.apigateway.controller;

import lombok.RequiredArgsConstructor;
import org.example.apigateway.global.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final ReactiveAuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(
            @RequestBody AuthRequest request
    ) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.username(), request.password());

        return authenticationManager.authenticate(authentication)
                .map(auth -> {
                    String token = jwtUtil.generateToken(auth.getName());
                    return ResponseEntity.ok(new AuthResponse(token));
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    public record AuthRequest(
            String username,
            String password
    ) {
    }

    public record AuthResponse(String token) {
    }
}
