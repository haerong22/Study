package com.userdemo.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebBookDto {

    private Long webBookId;
    //제목
    private String name;
    //설명
    private String description;

    private LocalDateTime createdAt;

}
