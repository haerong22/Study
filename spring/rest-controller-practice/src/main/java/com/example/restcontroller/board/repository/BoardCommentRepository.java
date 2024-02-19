package com.example.restcontroller.board.repository;

import com.example.restcontroller.board.entity.BoardComment;
import com.example.restcontroller.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {

    List<BoardComment> findByUser(User user);
}
