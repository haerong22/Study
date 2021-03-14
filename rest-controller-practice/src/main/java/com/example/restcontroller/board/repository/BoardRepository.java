package com.example.restcontroller.board.repository;

import com.example.restcontroller.board.entity.Board;
import com.example.restcontroller.board.entity.BoardType;
import com.example.restcontroller.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    long countByBoardType(BoardType boardType);

    List<Board> findByUser(User user);
}
