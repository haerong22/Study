package com.example.performance.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Notice implements Serializable {
    private long id;
    private String title;
    private String content;
    private String who;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}