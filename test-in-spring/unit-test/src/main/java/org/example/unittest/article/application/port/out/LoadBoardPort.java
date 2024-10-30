package org.example.unittest.article.application.port.out;

import org.example.unittest.article.domain.Board;

import java.util.Optional;

public interface LoadBoardPort {
    Optional<Board> findBoardById(Long boardId);
}