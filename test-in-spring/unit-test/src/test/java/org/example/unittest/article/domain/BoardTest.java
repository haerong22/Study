package org.example.unittest.article.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class BoardTest {

    @Test
    @DisplayName("Board Constructor")
    void constructBoard() {
        var board = new Board(1L, "name");

        then(board)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "name");
    }

}