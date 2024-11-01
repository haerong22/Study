package org.example.unittest.article.adapter.in.api;

import org.example.unittest.article.application.port.in.GetBoardUseCase;
import org.example.unittest.article.domain.Board;
import org.example.unittest.article.domain.BoardType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoardController {
    private final GetBoardUseCase boardUseCase;

    public BoardController(GetBoardUseCase boardUseCase) {
        this.boardUseCase = boardUseCase;
    }

    @GetMapping("/boards/{boardId}")
    Board getBoard(@PathVariable Long boardId) {
        return boardUseCase.getBoard(boardId);
    }

    @GetMapping("/boards")
    List<Board> listBoardByBoardType(@RequestParam BoardType boardType) {
        return boardUseCase.listBoardByBoardType(boardType);
    }
}
