package com.example.restcontroller.board.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.restcontroller.board.service.BoardService;
import com.example.restcontroller.common.model.ResponseResult;
import com.example.restcontroller.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ApiBoardBookmarkController {

    private final BoardService boardService;

    @PutMapping("/api/board/{id}/bookmark")
    public ResponseEntity<?> chapter3_17(@PathVariable Long id,
                                         @RequestHeader("TOKEN") String token) {

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        return ResponseResult.result(boardService.addBookmark(id, email));
    }

    @DeleteMapping("/api/bookmark/{id}")
    public ResponseEntity<?> chapter3_17_2(@PathVariable Long id,
                                         @RequestHeader("TOKEN") String token) {
        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }
        return ResponseResult.result(boardService.deleteBookmark(id, email));
    }
}