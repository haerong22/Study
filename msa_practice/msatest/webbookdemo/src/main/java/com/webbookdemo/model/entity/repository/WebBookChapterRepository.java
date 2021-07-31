package com.webbookdemo.model.entity.repository;

import com.webbookdemo.model.entity.WebBookChapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebBookChapterRepository extends JpaRepository<WebBookChapter,Long> {
    List<WebBookChapter> findAllByWebBookId(Long webBookId);
}
