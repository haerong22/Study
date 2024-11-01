package org.example.unittest.article.adapter.out.persistence.repository;

import org.example.unittest.article.adapter.out.persistence.entity.BoardJpaEntity;
import org.example.unittest.article.domain.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardJpaEntity, Long> {

    List<BoardJpaEntity> findAllByBoardType(BoardType boardType);
}