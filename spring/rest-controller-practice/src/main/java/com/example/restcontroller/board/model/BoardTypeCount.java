package com.example.restcontroller.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BoardTypeCount {

    private Long id;
    private String boardName;
    private LocalDateTime regDate;
    private boolean usingYn;
    private Long boardCount;

    public static BoardTypeCount of(Object[] arr) {
        return BoardTypeCount.builder()
                .id(((BigInteger) arr[0]).longValue())
                .boardName((String) arr[1])
                .regDate(((Timestamp) arr[2]).toLocalDateTime())
                .usingYn((Boolean) arr[3])
                .boardCount(((BigInteger) arr[4]).longValue())
                .build();

    }
}
