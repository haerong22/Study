package com.webbookdemo.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class WebBookChapter {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long webBookChapterId;
    //제목
    private Long webBookId;

    private String name;
    //내용
    private String detail;

    private LocalDateTime createdAt;

    private Integer price;
}
