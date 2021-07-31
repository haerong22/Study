package com.userdemo.model.dto;

import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReaderWebBookChapterDto {

    private Long webBookChapterId;
    private String name;
    private Integer price;
//    private Boolean isPaid;

}
