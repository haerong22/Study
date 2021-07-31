package com.userdemo.model.form;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WebBookChapterPaymentForm {
    private Long webBookChapterId;
    private Integer price;
}
