package com.example.restcontroller.notice.repository;

import com.example.restcontroller.notice.entity.Notice;
import com.example.restcontroller.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Optional<List<Notice>> findByIdIn(List<Long> idList);

    // 제목동일, 내용동일, 등록시간이 체크시간보다 크다.
    Optional<List<Notice>> findByTitleAndContentsAndRegDateIsGreaterThanEqual(String title, String contents, LocalDateTime regDate);

    int countByTitleAndContentsAndRegDateGreaterThanEqual(String title, String contents, LocalDateTime regDate);

    Optional<List<Notice>> findByUser(User user);

    int countByUser(User user);
}
