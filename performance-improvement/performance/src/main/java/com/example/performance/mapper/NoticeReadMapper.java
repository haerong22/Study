package com.example.performance.mapper;

import com.example.performance.dto.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface NoticeReadMapper {
    List<Notice> findAll();
    List<Notice> findByPage(int startIdx);

    List<Notice> findNoticesByDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}