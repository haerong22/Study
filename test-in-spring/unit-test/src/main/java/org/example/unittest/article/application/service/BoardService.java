package org.example.unittest.article.application.service;

import org.example.unittest.article.application.port.in.GetBoardUseCase;
import org.example.unittest.article.application.port.out.LoadBoardPort;
import org.example.unittest.article.domain.Board;
import org.example.unittest.article.domain.BoardType;
import org.example.unittest.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService implements GetBoardUseCase {

    private final LoadBoardPort loadBoardPort;

    public BoardService(LoadBoardPort loadBoardPort) {
        this.loadBoardPort = loadBoardPort;
    }

    @Override
    public Board getBoard(Long boardId) {
        return loadBoardPort.findBoardById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found"));
    }

    @Override
    public List<Board> listBoardByBoardType(BoardType boardType) {
        return loadBoardPort.findBoardsByBoardType(boardType);
    }
}
