package com.userdemo.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel
public class RegisterWriterForm {

    @ApiModelProperty(value = "작가의 이름", example = "홍길동")
    private String name;
}
