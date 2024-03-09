package com.example.performance.service;

import com.example.performance.dto.Notice;
import com.example.performance.mapper.NoticeReadMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeReadMapper noticeReadMapper;

    @Override
//    @Cacheable(
//            value = "NoticeReadMapper.findAll"
//    )
    public List<Notice> getAllNotices() {
        log.info("[SERVICE] getAllNotices");
        return noticeReadMapper.findAll();
    }

    @Override
    @Cacheable(
            value = "NoticeReadMapper.findByPage",
            key = "#request.requestURI + '-' + #pageNumber",
            condition = "#pageNumber <= 10"
    )
    public List<Notice> findByPage(HttpServletRequest request, int pageNumber) {
        log.info("[SERVICE] findByPage : {}", pageNumber);
        int startIdx = (pageNumber - 1) * 10;
        return noticeReadMapper.findByPage(startIdx);
    }
}