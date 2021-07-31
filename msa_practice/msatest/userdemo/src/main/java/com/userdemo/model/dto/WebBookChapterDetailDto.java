package com.userdemo.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebBookChapterDetailDto {

    private Long webBookChapterId;
    //제목
    private String name;
//    //내용
    private String detail;

    private LocalDateTime createdAt;

    private Integer price;

//    private Boolean isPaid;

}
