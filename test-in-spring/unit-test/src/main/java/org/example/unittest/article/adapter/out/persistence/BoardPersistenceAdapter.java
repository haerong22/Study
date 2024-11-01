package org.example.unittest.article.adapter.out.persistence;

import org.example.unittest.article.adapter.out.persistence.entity.BoardJpaEntity;
import org.example.unittest.article.adapter.out.persistence.repository.BoardRepository;
import org.example.unittest.article.application.port.out.LoadBoardPort;
import org.example.unittest.article.domain.Board;
import org.example.unittest.article.domain.BoardType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BoardPersistenceAdapter implements LoadBoardPort {
    private final BoardRepository boardRepository;

    public BoardPersistenceAdapter(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public Optional<Board> findBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .map(BoardJpaEntity::toDomain);
    }

    @Override
    public List<Board> findBoardsByBoardType(BoardType boardType) {
        return boardRepository.findAllByBoardType(boardType).stream()
                .map(BoardJpaEntity::toDomain)
                .toList();
    }
}