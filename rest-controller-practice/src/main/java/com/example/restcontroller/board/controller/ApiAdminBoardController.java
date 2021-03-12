package com.example.restcontroller.board.controller;

import com.example.restcontroller.board.entity.BoardReport;
import com.example.restcontroller.board.service.BoardService;
import com.example.restcontroller.common.model.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}