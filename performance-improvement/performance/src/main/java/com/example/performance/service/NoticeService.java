package com.example.performance.service;

import com.example.performance.dto.Notice;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface NoticeService {
    List<Notice> getAllNotices();

    List<Notice> findByPage(HttpServletRequest request, int pageNumber);

}