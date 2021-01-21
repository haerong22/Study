package com.example.jspblog.domain.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {

    private int id;
    private int userId;
    private String title;
    private String content;
    private int readCount;
    private Timestamp createDate;

    public String getTitle() {
        return title.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}
