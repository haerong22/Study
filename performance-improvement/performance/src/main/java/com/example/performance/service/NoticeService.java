package com.example.performance.service;

import com.example.performance.dto.Notice;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface NoticeService {
    List<Notice> getAllNotices();

    List<Notice> findByPage(HttpServletRequest request, int pageNumber);

    List<Notice> findNoticesByDates(LocalDateTime startDate, LocalDateTime endDate);
}