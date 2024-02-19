package com.example.jspblog.domain.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetailResDto {
    private int id;
    private String title;
    private String content;
    private int readCount;
    private String username;
    private int userId;

//    public String getContent() {
//        return content.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
//    }
}
