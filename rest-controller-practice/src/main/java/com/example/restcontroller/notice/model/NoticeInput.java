package com.example.restcontroller.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NoticeInput {

    @NotBlank(message = "제목을 입력 해주세요")
    @Size(min = 10, max = 50, message = "제목은 최소 10자 이상 최대 50자 입력 가능합니다.")
    private String title;

    @NotBlank(message = "내용을 입력 해주세요")
    @Length(min =30, max = 1000, message = "내용은 최소 50자 이상 최대 1000자 입력 가능합니다.")
    private String contents;
}
