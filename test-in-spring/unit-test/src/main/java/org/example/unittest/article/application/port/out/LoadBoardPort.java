package org.example.unittest.article.application.port.out;

import org.example.unittest.article.domain.Board;
import org.example.unittest.article.domain.BoardType;

import java.util.List;
import java.util.Optional;

public interface LoadBoardPort {
    Optional<Board> findBoardById(Long boardId);

    List<Board> findBoardsByBoardType(BoardType boardType);
}