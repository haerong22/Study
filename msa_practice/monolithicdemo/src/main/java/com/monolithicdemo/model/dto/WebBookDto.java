package com.monolithicdemo.model.dto;

import com.monolithicdemo.model.entity.WebBook;
import lombok.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebBookDto {
    private Long webBookId;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    public static WebBookDto from(WebBook webBook) {
        return WebBookDto.builder()
                .webBookId(webBook.getWebBookId())
                .name(webBook.getName())
                .description(webBook.getDescription())
                .createdAt(webBook.getCreatedAt())
                .build();
    }
}
