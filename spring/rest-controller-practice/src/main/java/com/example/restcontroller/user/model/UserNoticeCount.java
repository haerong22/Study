package com.example.restcontroller.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserNoticeCount {

    private Long id;
    private String email;
    private String userName;

    private Long noticeCount;

    public static UserNoticeCount of(Object[] data) {
        return UserNoticeCount.builder()
                .id((Long) data[0])
                .email((String) data[1])
                .userName((String) data[2])
                .noticeCount((Long) data[3])
                .build();
    }

}
