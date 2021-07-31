package com.userdemo.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel
public class RegisterWebBookChapterForm {

    @ApiModelProperty(value = "웹소설 제목", example = "MSA 실습")
    private String name;

    @ApiModelProperty(value = "웹소설 설명", example = "blah, blah ~~~")
    private String detail;

    @ApiModelProperty(value = "웹소설 가격", example = "100")
    private Integer price;

}
