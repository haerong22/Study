package com.example.restcontroller.user.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.restcontroller.board.model.ServiceResult;
import com.example.restcontroller.common.model.ResponseResult;
import com.example.restcontroller.user.service.UserService;
import com.example.restcontroller.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiUserInterestController {

    private final UserService userService;

    @PutMapping("/api/user/{id}/interest")
    public ResponseEntity<?> chapter3_18(@PathVariable Long id,
                                         @RequestHeader("TOKEN") String token) {
        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }
        ServiceResult result = userService.addInterestUser(id, email);
        return ResponseResult.result(result);
    }
}
