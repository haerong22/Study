package org.example.unittest.article.application.service;

import org.example.unittest.article.application.port.out.LoadBoardPort;
import org.example.unittest.article.domain.Board;
import org.example.unittest.article.domain.BoardFixtures;
import org.example.unittest.article.domain.BoardType;
import org.example.unittest.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.stream.LongStream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * - getBoard(boardId)
 * - Repository 에서 조회되면 PersistenceAdapter 에서 Board 반환
 * - Repository 에서 조회되지 않으면 ResourceNotFoundException throw
 */
class BoardServiceTest {
    private BoardService sut;

    private LoadBoardPort loadBoardPort = Mockito.mock(LoadBoardPort.class);

    @BeforeEach
    void setUp() {
        sut = new BoardService(loadBoardPort);
    }

    @Nested
    @DisplayName("test getBoard(boardId)")
    class GetBoard {

        @Test
        @DisplayName("Board가 존재하면 Board 반환")
        void givenBoardThenReturnBoard() {
            // given
            var boardId = 1L;
            var board = BoardFixtures.board(boardId);
            given(loadBoardPort.findBoardById(boardId))
                    .willReturn(Optional.of(board));

            // when
            Board result = sut.getBoard(boardId);

            // then
            then(result)
                    .isNotNull()
                    .hasFieldOrPropertyWithValue("id", boardId)
                    .hasFieldOrPropertyWithValue("name", board.getName());
        }

        @Test
        @DisplayName("Board가 존재하지 않으면 ResourceNotFoundException throw")
        void notExistBoardThenThrowResourceNotFoundException() {
            var boardId = 1L;
            given(loadBoardPort.findBoardById(boardId))
                    .willReturn(Optional.empty());

            thenThrownBy(() -> sut.getBoard(boardId))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("Board not found");
        }
    }

    @Nested
    @DisplayName("test listBoardsByBoardType(boardType)")
    class ListBoardsByBoardType {

        @Test
        @DisplayName("BoardType에 해당하는 Board 목록을 반환")
        void returnBoardList() {
            var list = LongStream.range(1L, 4L)
                    .mapToObj(BoardFixtures::board)
                    .toList();
            given(loadBoardPort.findBoardsByBoardType(any()))
                    .willReturn(list);

            var result = sut.listBoardByBoardType(BoardType.GENERAL);

            then(result)
                    .hasSize(3)
                    .extracting("boardType").containsOnly(BoardType.GENERAL);
        }
    }
}