package com.monolithicdemo.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebBookChapterDto {
    private Long webBookChapterId;
    private Long webBookId;
    private String name;
    private String detail;
    private LocalDateTime createdAt;
    private Integer price;


}
