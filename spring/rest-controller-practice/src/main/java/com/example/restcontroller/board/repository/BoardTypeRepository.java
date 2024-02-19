package com.example.restcontroller.board.repository;

import com.example.restcontroller.board.entity.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardTypeRepository extends JpaRepository<BoardType, Long> {
    int countByBoardName(String boardName);
}
