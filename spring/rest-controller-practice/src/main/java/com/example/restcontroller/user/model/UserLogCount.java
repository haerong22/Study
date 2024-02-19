package com.example.restcontroller.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLogCount {

    private Long id;
    private String email;
    private String userName;

    private Long noticeCount;
    private Long noticeLikeCount;
}
