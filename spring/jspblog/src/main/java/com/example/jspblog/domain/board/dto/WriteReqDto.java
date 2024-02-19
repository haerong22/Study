package com.example.jspblog.domain.board.dto;

import lombok.Data;

@Data
public class WriteReqDto {

    private int userId;
    private String title;
    private String content;
}
