package org.example.unittest.article.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.unittest.article.domain.Board;
import org.example.unittest.article.domain.BoardType;

@Entity
@Table(name = "board")
@NoArgsConstructor
@Getter
public class BoardJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    public BoardJpaEntity(String name, BoardType boardType) {
        this.name = name;
        this.boardType = boardType;
    }

    public BoardJpaEntity(Long id, String name, BoardType boardType) {
        this.id = id;
        this.name = name;
        this.boardType = boardType;
    }

    public Board toDomain() {
        return new Board(this.id, this.name, this.boardType);
    }

    public static BoardJpaEntity fromDomain(Board board) {
        return new BoardJpaEntity(board.getId(), board.getName(), board.getBoardType());
    }
}