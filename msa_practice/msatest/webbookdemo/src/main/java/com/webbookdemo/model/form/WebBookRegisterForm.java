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
public class WebBookRegisterForm {

    private Long writerId;

    @ApiModelProperty(value = "웹소설 제목", example = "MSA 실습")
    private String name;

    @ApiModelProperty(value = "웹소설 설명", example = "Monolithic 에서 MSA 로 실습을 해 봅시다.")
    private String description;

}
