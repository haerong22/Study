package org.example.unittest.article.application.port.in;

import org.example.unittest.article.domain.Board;
import org.example.unittest.article.domain.BoardType;

import java.util.List;

public interface GetBoardUseCase {
    Board getBoard(Long boardId);

    List<Board> listBoardByBoardType(BoardType boardType);
}
