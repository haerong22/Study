package com.example.test_demo.user.controller;

import com.example.test_demo.user.controller.port.AuthenticationService;
import com.example.test_demo.user.controller.port.UserCreateService;
import com.example.test_demo.user.controller.port.UserReadService;
import com.example.test_demo.user.controller.port.UserUpdateService;
import com.example.test_demo.user.controller.response.MyProfileResponse;
import com.example.test_demo.user.controller.response.UserResponse;
import com.example.test_demo.user.domain.User;
import com.example.test_demo.user.domain.UserUpdate;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Builder
@Tag(name = "유저(users)")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserCreateService userCreateService;
    private final UserUpdateService userUpdateService;
    private final UserReadService userReadService;
    private final AuthenticationService authenticationService;

    @ResponseStatus
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
        return ResponseEntity
            .ok()
            .body(UserResponse.from(userReadService.getById(id)));
    }

    @GetMapping("/{id}/verify")
    public ResponseEntity<Void> verifyEmail(
        @PathVariable long id,
        @RequestParam String certificationCode) {
        authenticationService.verifyEmail(id, certificationCode);
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create("http://localhost:3000"))
            .build();
    }

    @GetMapping("/me")
    public ResponseEntity<MyProfileResponse> getMyInfo(
        @Parameter(name = "EMAIL", in = ParameterIn.HEADER)
        @RequestHeader("EMAIL") String email // 일반적으로 스프링 시큐리티를 사용한다면 UserPrincipal 에서 가져옵니다.
    ) {
        User user = userReadService.getByEmail(email);
        authenticationService.login(user.getId());
        user = userReadService.getByEmail(email);
        return ResponseEntity
            .ok()
            .body(MyProfileResponse.from(user));
    }

    @PutMapping("/me")
    @Parameter(in = ParameterIn.HEADER, name = "EMAIL")
    public ResponseEntity<MyProfileResponse> updateMyInfo(
        @Parameter(name = "EMAIL", in = ParameterIn.HEADER)
        @RequestHeader("EMAIL") String email, // 일반적으로 스프링 시큐리티를 사용한다면 UserPrincipal 에서 가져옵니다.
        @RequestBody UserUpdate userUpdate
    ) {
        User user = userReadService.getByEmail(email);
        user = userUpdateService.update(user.getId(), userUpdate);
        return ResponseEntity
            .ok()
            .body(MyProfileResponse.from(user));
    }

}