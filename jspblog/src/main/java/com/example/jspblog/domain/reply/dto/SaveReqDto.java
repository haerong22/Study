package com.example.jspblog.domain.reply.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveReqDto {

    private int userId;
    private int boardId;
    private String content;
}
