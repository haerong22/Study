package com.webbookdemo.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class WebBookChapterRegisterForm {

    private Long webBookId;

    @ApiModelProperty(value = "웹소설 제목", example = "MSA 실습")
    private String name;

    @ApiModelProperty(value = "웹소설 내용", example = "Blah ~~.")
    private String detail;

    private Integer price;

}
