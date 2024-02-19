package com.example.restcontroller.notice.model;

import com.example.restcontroller.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeResponse {

    private Long id;

    private Long regUserId;
    private String regUserName;

    private String title;
    private String contents;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private Integer hits;
    private Integer likes;

    public static NoticeResponse of(Notice notice) {
        return NoticeResponse.builder()
                .id(notice.getId())
                .regUserId(notice.getUser().getId())
                .regUserName(notice.getUser().getUserName())
                .title(notice.getTitle())
                .contents(notice.getContents())
                .regDate(notice.getRegDate())
                .updateDate(notice.getUpdateDate())
                .hits(notice.getHits())
                .likes(notice.getLikes())
                .build();
    }
}
