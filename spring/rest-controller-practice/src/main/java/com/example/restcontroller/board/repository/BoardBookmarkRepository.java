package com.example.restcontroller.board.repository;

import com.example.restcontroller.board.entity.BoardBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardBookmarkRepository extends JpaRepository<BoardBookmark, Long> {
}
