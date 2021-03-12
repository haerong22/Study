package com.example.restcontroller.board.repository;

import com.example.restcontroller.board.entity.Board;
import com.example.restcontroller.board.entity.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    long countByBoardType(BoardType boardType);
}
