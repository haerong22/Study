package com.monolithicdemo.repository;

import com.monolithicdemo.model.entity.WebBookChapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebBookChapterRepository extends JpaRepository<WebBookChapter,Long> {
}
