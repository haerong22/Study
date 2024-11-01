package org.example.unittest.article.adapter.in.api;

import org.example.unittest.article.application.port.in.GetBoardUseCase;
import org.example.unittest.article.domain.BoardFixtures;
import org.example.unittest.article.domain.BoardType;
import org.example.unittest.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.LongStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * - GET /boards/{boardId}
 * - GET /boards?boardType={boardType}
 */
@WebMvcTest(BoardController.class)
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetBoardUseCase getBoardUseCase;

    @Nested
    @DisplayName("GET /boards/{boardId}")
    class GetBoard {

        @Test
        @DisplayName("boardId에 해당하는 Board 정보 반환, 200 OK")
        void testGetBoardThen200Ok() throws Exception {
            var boardId = 1L;
            var board = BoardFixtures.board(boardId);
            given(getBoardUseCase.getBoard(any())).willReturn(board);

            mockMvc
                    .perform(
                            get("/boards/{boardId}", boardId)
                    )
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.id").value(boardId),
                            jsonPath("$.name").value(board.getName())

                    );
        }

        @Test
        @DisplayName("boardId에 해당하는 Board가 없으면 400 Not Found")
        void testGetBoardThen400NotFound() throws Exception {
            var boardId = 1L;
            given(getBoardUseCase.getBoard(any())).willThrow(new ResourceNotFoundException(""));

            mockMvc
                    .perform(
                            get("/boards/{boardId}", boardId)
                    )
                    .andExpectAll(
                            status().isNotFound()
                    );
        }
    }

    @Nested
    @DisplayName("GET /boards?boardType={boardType}")
    class ListBoardByBoardType {
        @Test
        void testListBoardByBoardType() throws Exception {
            // boardType = GENERAL, IMAGE
            var list = LongStream.range(1L, 4L)
                    .mapToObj(BoardFixtures::board)
                    .toList();
            given(getBoardUseCase.listBoardByBoardType(any()))
                    .willReturn(list);
            mockMvc
                    .perform(
                            get("/boards?boardType={boardType}", BoardType.GENERAL)
                    )
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.size()").value(3),
                            jsonPath("$.[0].boardType").value(BoardType.GENERAL.toString())
                    );
        }
    }

}