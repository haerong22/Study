package com.monolithicdemo.model.dto;

import com.monolithicdemo.model.entity.WebBookChapter;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebBookChapterPaidDto {
    private Long webBookChapterId;
    private String name;
    private String detail;
    private LocalDateTime createdAt;
    private Integer price;
    private Boolean isPaid;

    public static WebBookChapterPaidDto from(WebBookChapter webBookChapter) {
        return WebBookChapterPaidDto.builder()
                .webBookChapterId(webBookChapter.getWebBookChapterId())
                .name(webBookChapter.getName())
                .detail(webBookChapter.getDetail())
                .createdAt(webBookChapter.getCreatedAt())
                .price(webBookChapter.getPrice())
                .build();
    }
}
