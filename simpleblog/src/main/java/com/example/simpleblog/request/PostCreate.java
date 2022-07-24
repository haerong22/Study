package com.example.simpleblog.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostCreate {

    @NotBlank(message = "제목을 입력해주세요.")
    private final String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;

    /**
     * Builder
     * - 가독성
     * - 필요한 값만 받아서 생성 가능
     * - 객체의 불변성
     */
    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
