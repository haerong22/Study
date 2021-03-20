package com.example.restcontroller.board.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.restcontroller.board.entity.Board;
import com.example.restcontroller.board.entity.BoardType;
import com.example.restcontroller.board.model.*;
import com.example.restcontroller.board.service.BoardService;
import com.example.restcontroller.common.exception.BizException;
import com.example.restcontroller.common.model.ResponseResult;
import com.example.restcontroller.notice.model.ResponseError;
import com.example.restcontroller.user.model.ResponseMessage;
import com.example.restcontroller.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiBoardController {

    private final BoardService boardService;

    @PostMapping("/api/board/type")
    public ResponseEntity<?> chapter3_1(@RequestBody @Valid BoardTypeInput boardTypeInput, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            List<ResponseError> responseErrors = ResponseError.of(bindingResult.getFieldErrors());
            ResponseEntity.badRequest().body(ResponseMessage.fail("입력값이 정확하지 않습니다.", responseErrors));
        }

        ServiceResult result = boardService.addBoard(boardTypeInput);

        if (!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @PutMapping("/api/board/type/{id}")
    public ResponseEntity<?> chapter3_2(@PathVariable Long id,
                                        @RequestBody @Valid BoardTypeInput boardTypeInput, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            List<ResponseError> responseErrors = ResponseError.of(bindingResult.getFieldErrors());
            ResponseEntity.badRequest().body(ResponseMessage.fail("입력값이 정확하지 않습니다.", responseErrors));
        }
        System.out.println(boardTypeInput.getName());
        ServiceResult result = boardService.updateBoard(id, boardTypeInput);


        if (!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @DeleteMapping("/api/board/type/{id}")
    public ResponseEntity<?> chapter3_3(@PathVariable Long id) {
        ServiceResult result = boardService.deleteBoard(id);

        if (!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @GetMapping("/api/board/type")
    public ResponseEntity<?> chapter3_4() {
        List<BoardType> boardTypeList = boardService.getBoardTypeList();

        return ResponseEntity.ok().body(ResponseMessage.success(boardTypeList));
    }

    @PatchMapping("/api/board/type/{id}/using")
    public ResponseEntity<?> chapter3_5(@PathVariable Long id, @RequestBody BoardTypeUsing boardTypeUsing) {
        ServiceResult result = boardService.setBoardTypeUsing(id, boardTypeUsing);

        if (!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @GetMapping("/api/board/type/count")
    public ResponseEntity<?> chapter3_6() {
        List<BoardTypeCount> list = boardService.getBoardTypeCount();

        return ResponseEntity.ok().body(ResponseMessage.success(list));
    }

    @PatchMapping("/api/board/{id}/top")
    public ResponseEntity<?> chapter3_7(@PathVariable Long id) {
        ServiceResult result = boardService.setBoardTop(id, true);

        if (!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @PatchMapping("/api/board/{id}/top/clear")
    public ResponseEntity<?> chapter3_8(@PathVariable Long id) {
        ServiceResult result = boardService.setBoardTop(id, false);

        if (!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @PatchMapping("/api/board/{id}/publish")
    public ResponseEntity<?> chapter3_9(@PathVariable Long id, @RequestBody BoardPeriod boardPeriod) {
        ServiceResult result = boardService.setBoardPeriod(id, boardPeriod);

        if (!result.isResult()) {
            return ResponseResult.fail(result.getMessage());
        }

        return ResponseResult.success();
    }

    @PutMapping("/api/board/{id}/hits")
    public ResponseEntity<?> chapter3_10(@PathVariable Long id, @RequestHeader("TOKEN") String token) {

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = boardService.setBoardHits(id, email);
        if (result.isFail()) {
            return ResponseResult.fail(result.getMessage());
        }

        return ResponseResult.success();
    }

    @PutMapping("/api/board/{id}/like")
    public ResponseEntity<?> chapter3_11(@PathVariable Long id, @RequestHeader("TOKEN") String token) {
        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = boardService.setBoardLike(id, email);
        return ResponseResult.result(result);
    }

    @PutMapping("/api/board/{id}/unlike")
    public ResponseEntity<?> chapter3_12(@PathVariable Long id, @RequestHeader("TOKEN") String token) {
        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = boardService.setBoardUnLike(id, email);
        return ResponseResult.result(result);
    }

    @PutMapping("/api/board/{id}/report")
    public ResponseEntity<?> chapter3_13(@PathVariable Long id,
                                         @RequestHeader("TOKEN") String token,
                                         @RequestBody BoardReportInput boardReportInput) {

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }
        ServiceResult result = boardService.addReport(id, email, boardReportInput);

        return ResponseResult.result(result);
    }

    @GetMapping("/api/board/{id}")
    public ResponseEntity<?> chapter4_3(@PathVariable Long id) {

        Board board = null;
        try {
            board = boardService.detail(id);
        } catch (BizException e) {
            return ResponseResult.fail(e.getMessage());
        }
        return ResponseResult.success(board);
    }

    @GetMapping("/api/board")
    public ResponseEntity<?> chapter4_10() {

        List<Board> list = boardService.list();

        return ResponseResult.success(list);
    }

    @PostMapping("/api/board")
    public ResponseEntity<?> chapter4_11(@RequestBody BoardInput boardInput,
                                         @RequestHeader("TOKEN") String token) {

        String email = JWTUtils.getIssuer(token);

        ServiceResult result = boardService.add(email, boardInput);
        return ResponseResult.result(result);
    }
}