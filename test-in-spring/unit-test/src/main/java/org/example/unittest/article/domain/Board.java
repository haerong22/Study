package org.example.unittest.article.domain;

import lombok.Getter;

@Getter
public class Board {
    private Long id;
    private String name;
    private BoardType boardType;

    public Board(Long id, String name, BoardType boardType) {
        this.id = id;
        this.name = name;
        this.boardType = boardType;
    }
}