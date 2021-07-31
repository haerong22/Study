package com.userdemo.model.entity.repository;

import com.userdemo.model.entity.ReaderWebBookChapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderWebBookChapterRepository extends JpaRepository<ReaderWebBookChapter,Long> {

    ReaderWebBookChapter findByReaderIdAndWebBookChapterId(Long readerId, Long webBookChapterId);

}
