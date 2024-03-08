package com.example.performance.controller;

import com.example.performance.dto.Notice;
import com.example.performance.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<Notice> notices = noticeService.getAllNotices();
        return new ResponseEntity<>(notices, HttpStatus.OK);
    }

    @GetMapping("/{page}")
    public ResponseEntity<Object> findByPage(HttpServletRequest request, @PathVariable("page") Integer page) {
        List<Notice> notices = noticeService.findByPage(request, page);
        return new ResponseEntity<>(notices, HttpStatus.OK);
    }
}