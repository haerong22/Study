package com.example.restcontroller.board.repository;

import com.example.restcontroller.board.entity.Board;
import com.example.restcontroller.board.entity.BoardHits;
import com.example.restcontroller.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardHitsRepository extends JpaRepository<BoardHits, Long> {

    long countByBoardAndUser(Board board, User user);
}
