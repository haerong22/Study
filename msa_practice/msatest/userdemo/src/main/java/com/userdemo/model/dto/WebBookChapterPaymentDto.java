package com.userdemo.model.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebBookChapterPaymentDto {

    private Long writerId;  // null
    private Long readerId;  //
    private Long webBookChapterId;
    private Integer amount;
}
