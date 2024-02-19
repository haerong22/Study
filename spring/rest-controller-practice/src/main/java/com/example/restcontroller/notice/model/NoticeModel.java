package com.example.restcontroller.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@Data
public class NoticeModel {

    //ID, 제목, 내용, 등록일(작성일)
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime regDate;

}
