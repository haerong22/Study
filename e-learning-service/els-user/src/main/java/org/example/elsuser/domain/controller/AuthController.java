package org.example.elsuser.domain.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.elsuser.domain.controller.swagger.AuthControllerSwagger;
import org.example.elsuser.domain.dto.AuthRequest;
import org.example.elsuser.domain.dto.TokenRequest;
import org.example.elsuser.domain.entity.User;
import org.example.elsuser.domain.service.JWTService;
import org.example.elsuser.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerSwagger {

    private final JWTService jwtService;
    private final UserService userService;

    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> generateToken(
            HttpServletRequest request,
            @RequestBody AuthRequest authRequest
    ) {
        User existingUser = userService.getUserByEmail(authRequest.getEmail());
        String token = jwtService.generateToken(existingUser, authRequest.getPassword());
        String ipAddress = request.getRemoteAddr();

        userService.logUserLogin(existingUser, ipAddress);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @PostMapping("/validate")
    public ResponseEntity<User> validateToken(
            @RequestBody TokenRequest tokenRequest
    ) {
        Claims claims = jwtService.parseJwtClaims(tokenRequest.getToken());
        return ResponseEntity.ok(userService.getUserByEmail(claims.getSubject()));
    }

    @PostMapping("/verify-token")
    public ResponseEntity<Map<String, Boolean>> verifyToken(
            @RequestBody TokenRequest tokenRequest
    ) {
        boolean isValid = jwtService.validateToken(tokenRequest.getToken());
        return ResponseEntity.ok(Collections.singletonMap("isValid", isValid));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(
            @RequestBody TokenRequest tokenRequest
    ) {
        String newToken = jwtService.refreshToken(tokenRequest.getToken());
        return ResponseEntity.ok(Collections.singletonMap("token", newToken));
    }
}