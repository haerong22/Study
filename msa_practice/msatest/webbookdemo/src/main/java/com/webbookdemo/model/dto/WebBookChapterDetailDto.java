package com.webbookdemo.model.dto;

import com.webbookdemo.model.entity.WebBookChapter;
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


    public static WebBookChapterDetailDto from(WebBookChapter webBookChapter){
        return WebBookChapterDetailDto.builder()
                .webBookChapterId(webBookChapter.getWebBookChapterId())
                .name(webBookChapter.getName())
                .detail(webBookChapter.getDetail())
                .createdAt(webBookChapter.getCreatedAt())
                .price(webBookChapter.getPrice())
                .build();
    }
}
