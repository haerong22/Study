package com.example.springbatch.domain.repository;

import com.example.springbatch.domain.entity.Board;
import com.example.springbatch.domain.entity.OddBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OddBoardRepository extends JpaRepository<OddBoard, Long> {
}
