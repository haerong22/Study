package com.example.performance.mapper;

import com.example.performance.dto.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeReadMapper {
    List<Notice> findAll();
    List<Notice> findByPage(int startIdx);
}