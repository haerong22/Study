package com.example.jspblog.domain.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LocalDateTime createDate;
}
