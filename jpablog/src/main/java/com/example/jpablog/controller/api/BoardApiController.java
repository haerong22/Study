package com.example.jpablog.controller.api;

import com.example.jpablog.config.auth.PrincipalDetail;
import com.example.jpablog.dto.ResponseDto;
import com.example.jpablog.model.Board;
import com.example.jpablog.model.User;
import com.example.jpablog.service.BoardService;
import com.example.jpablog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal, Principal p) {
        System.out.println(p.getName());
        boardService.글쓰기(board, principal.getUser());
        return new ResponseDto<>(1, HttpStatus.OK.value());
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable Long id) {
        boardService.글삭제하기(id);
        return new ResponseDto<>(1, HttpStatus.OK.value());
    }

    /*// 기본 로그인
    @PostMapping("/user/login")
    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
        User principal = userService.로그인(user);
        if (principal != null) {
            session.setAttribute("principal", principal);
        }
        return new ResponseDto<>(1, HttpStatus.OK.value());
    }*/
}
