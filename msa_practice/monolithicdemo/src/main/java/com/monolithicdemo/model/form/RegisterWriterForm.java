package com.monolithicdemo.model.form;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterWriterForm {

    @ApiParam(value = "작가의 이름", example = "홍길동", defaultValue = "홍길동")
    private String name;
}
