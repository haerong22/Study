package org.example.unittest.article.domain;

public class BoardFixtures {
    public static Board board() {
        return new Board(5L, "board", BoardType.GENERAL);
    }

    public static Board board(Long boardId) {
        return new Board(boardId, "board", BoardType.GENERAL);
    }
}