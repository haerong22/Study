package com.example.restcontroller.board.controller;

import com.example.restcontroller.board.entity.BoardReport;
import com.example.restcontroller.board.model.BoardReplyInput;
import com.example.restcontroller.board.model.ServiceResult;
import com.example.restcontroller.board.service.BoardService;
import com.example.restcontroller.common.model.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiAdminBoardController {
    private final BoardService boardService;

    @GetMapping("/api/admin/board/report")
    public ResponseEntity<?> chapter3_14() {
        List<BoardReport> list = boardService.boardReportList();
        return ResponseResult.success(list);
    }

    @PostMapping("/api/admin/board/{id}/reply")
    public ResponseEntity<?> chapter5_5(@PathVariable Long id,
                                        @RequestBody BoardReplyInput boardReplyInput) {

        ServiceResult result = boardService.replyBoard(id, boardReplyInput);
        return ResponseResult.result(result);
    }
}